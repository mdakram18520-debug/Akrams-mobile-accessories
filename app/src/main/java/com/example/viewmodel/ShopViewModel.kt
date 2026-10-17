package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.CartItem
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.model.PaymentMethod
import com.example.model.Product
import com.example.model.ProductCategory
import com.example.model.SavedCard
import com.example.model.SupportTicket
import com.example.model.TrackingEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

enum class AppTab {
  HOME,
  ACCOUNT
}

data class ShopUiState(
  val currentTab: AppTab = AppTab.HOME,
  val selectedCategory: ProductCategory = ProductCategory.ALL,
  val searchQuery: String = "",
  val cartItems: List<CartItem> = emptyList(),
  val selectedPaymentMethod: PaymentMethod = PaymentMethod.PREPAID_UPI,
  val promoCode: String = "",
  val isPromoApplied: Boolean = false,
  val savedCards: List<SavedCard> = SampleData.sampleSavedCards,
  val orders: List<Order> = SampleData.sampleOrders,
  val supportTickets: List<SupportTicket> = listOf(
    SupportTicket(
      id = "TKT-4412",
      category = "Order Delivery",
      message = "When will my AeroPods Pro arrive?",
      createdAt = "Today, 11:30 AM",
      status = "Resolved",
      agentResponse = "Hi Akram! Your order is out for delivery with courier partner Akram Express and will reach your address by 8:00 PM today."
    )
  ),
  val selectedProductForDetail: Product? = null,
  val isCartSheetOpen: Boolean = false,
  val selectedOrderForTracking: Order? = null,
  val isAddCardDialogOpen: Boolean = false,
  val isSupportQueryDialogOpen: Boolean = false,
  val lastPlacedOrder: Order? = null,
  val feedbackMessage: String? = null
) {
  val subtotal: Int
    get() = cartItems.sumOf { it.product.price * it.quantity }

  val discount: Int
    get() = if (isPromoApplied) 100 else 0

  // FREE delivery on prepaid orders, ₹50 on Cash on Delivery
  val deliveryFee: Int
    get() = if (cartItems.isEmpty()) 0 else selectedPaymentMethod.deliveryCharge

  val totalAmount: Int
    get() = if (cartItems.isEmpty()) 0 else maxOf(0, subtotal - discount + deliveryFee)

  val cartItemCount: Int
    get() = cartItems.sumOf { it.quantity }

  val activeOrdersCount: Int
    get() = orders.count { it.status != OrderStatus.DELIVERED }
}

class ShopViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(ShopUiState())
  val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

  val allProducts: List<Product> = SampleData.products

  fun setTab(tab: AppTab) {
    _uiState.update { it.copy(currentTab = tab) }
  }

  fun setCategory(category: ProductCategory) {
    _uiState.update { it.copy(selectedCategory = category) }
  }

  fun setSearchQuery(query: String) {
    _uiState.update { it.copy(searchQuery = query) }
  }

  fun openProductDetail(product: Product) {
    _uiState.update { it.copy(selectedProductForDetail = product) }
  }

  fun closeProductDetail() {
    _uiState.update { it.copy(selectedProductForDetail = null) }
  }

  fun openCart() {
    _uiState.update { it.copy(isCartSheetOpen = true) }
  }

  fun closeCart() {
    _uiState.update { it.copy(isCartSheetOpen = false) }
  }

  fun addToCart(product: Product, color: String = product.colors.first()) {
    _uiState.update { state ->
      val existing = state.cartItems.find { it.product.id == product.id && it.selectedColor == color }
      val updatedList = if (existing != null) {
        state.cartItems.map {
          if (it.product.id == product.id && it.selectedColor == color) {
            it.copy(quantity = it.quantity + 1)
          } else it
        }
      } else {
        state.cartItems + CartItem(product = product, quantity = 1, selectedColor = color)
      }
      state.copy(
        cartItems = updatedList,
        feedbackMessage = "Added ${product.name} to cart (₹${product.price})"
      )
    }
  }

  fun updateCartQuantity(productId: String, selectedColor: String, delta: Int) {
    _uiState.update { state ->
      val updatedList = state.cartItems.mapNotNull { item ->
        if (item.product.id == productId && item.selectedColor == selectedColor) {
          val newQty = item.quantity + delta
          if (newQty > 0) item.copy(quantity = newQty) else null
        } else item
      }
      state.copy(cartItems = updatedList)
    }
  }

  fun removeFromCart(productId: String, selectedColor: String) {
    _uiState.update { state ->
      state.copy(
        cartItems = state.cartItems.filterNot { it.product.id == productId && it.selectedColor == selectedColor }
      )
    }
  }

  fun setPaymentMethod(method: PaymentMethod) {
    _uiState.update { it.copy(selectedPaymentMethod = method) }
  }

  fun applyPromoCode(code: String) {
    val clean = code.trim().uppercase()
    if (clean == "AKRAM100" || clean == "FREESHIP" || clean == "FESTIVE") {
      _uiState.update {
        it.copy(
          promoCode = clean,
          isPromoApplied = true,
          feedbackMessage = "Promo code $clean applied! ₹100 instant discount."
        )
      }
    } else {
      _uiState.update {
        it.copy(feedbackMessage = "Invalid code! Try promo code AKRAM100")
      }
    }
  }

  fun placeOrder(): Order? {
    val currentState = _uiState.value
    if (currentState.cartItems.isEmpty()) return null

    val randomNum = Random.nextInt(10000, 99999)
    val orderNum = "AKM-$randomNum"
    val isCod = currentState.selectedPaymentMethod == PaymentMethod.CASH_ON_DELIVERY
    val fee = if (isCod) 50 else 0

    val newOrder = Order(
      id = "ord_$randomNum",
      orderNumber = orderNum,
      items = currentState.cartItems,
      orderDate = "Just now",
      expectedDelivery = "Within 2-3 business days",
      deliveryAddress = SampleData.sampleAddress,
      paymentMethod = currentState.selectedPaymentMethod,
      subtotal = currentState.subtotal,
      deliveryFee = fee,
      discount = currentState.discount,
      totalAmount = currentState.totalAmount,
      status = OrderStatus.PLACED,
      trackingNumber = "AKM-TRK-$randomNum",
      courierName = if (isCod) "Akram Logistics (COD Service)" else "Akram Express Priority (Free Prepaid)",
      trackingEvents = listOf(
        TrackingEvent(
          title = "Order Confirmed",
          location = "Akram's Mobile Hub Online",
          timestamp = "Just now",
          isCompleted = true,
          isCurrent = true
        ),
        TrackingEvent(
          title = if (isCod) "Cash on Delivery payment pending (₹${currentState.totalAmount})" else "Prepaid payment confirmed (₹0 Free Delivery)",
          location = "Online Payment Gateway",
          timestamp = "Just now",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Awaiting Warehouse Dispatch",
          location = "Akram Fulfillment Hub, Indiranagar",
          timestamp = "Estimated today",
          isCompleted = false,
          isCurrent = false
        )
      )
    )

    _uiState.update { state ->
      state.copy(
        orders = listOf(newOrder) + state.orders,
        cartItems = emptyList(),
        isCartSheetOpen = false,
        lastPlacedOrder = newOrder,
        feedbackMessage = "Order $orderNum placed successfully!"
      )
    }
    return newOrder
  }

  fun clearLastPlacedOrder() {
    _uiState.update { it.copy(lastPlacedOrder = null) }
  }

  // Order Tracking
  fun openOrderTracking(order: Order) {
    _uiState.update { it.copy(selectedOrderForTracking = order) }
  }

  fun closeOrderTracking() {
    _uiState.update { it.copy(selectedOrderForTracking = null) }
  }

  // Card Management
  fun openAddCardDialog() {
    _uiState.update { it.copy(isAddCardDialogOpen = true) }
  }

  fun closeAddCardDialog() {
    _uiState.update { it.copy(isAddCardDialogOpen = false) }
  }

  fun addNewCard(cardNumber: String, holderName: String, expiry: String, cardType: String, bankName: String) {
    val cleanDigits = cardNumber.filter { it.isDigit() }
    val last4 = if (cleanDigits.length >= 4) cleanDigits.takeLast(4) else "1234"
    val masked = "•••• •••• •••• $last4"

    val newCard = SavedCard(
      id = "card_${System.currentTimeMillis()}",
      cardNumberMasked = masked,
      cardHolderName = holderName.trim().uppercase(),
      expiryDate = expiry.trim(),
      cardType = cardType,
      bankName = bankName.ifBlank { "National Bank" },
      isDefault = _uiState.value.savedCards.isEmpty()
    )

    _uiState.update { state ->
      state.copy(
        savedCards = state.savedCards + newCard,
        isAddCardDialogOpen = false,
        feedbackMessage = "Card ending in $last4 added successfully!"
      )
    }
  }

  fun deleteCard(cardId: String) {
    _uiState.update { state ->
      val filtered = state.savedCards.filterNot { it.id == cardId }
      // If we removed default, mark the first one as default
      val updated = if (filtered.isNotEmpty() && filtered.none { it.isDefault }) {
        filtered.mapIndexed { idx, c -> if (idx == 0) c.copy(isDefault = true) else c }
      } else filtered
      state.copy(
        savedCards = updated,
        feedbackMessage = "Card removed from wallet."
      )
    }
  }

  fun setDefaultCard(cardId: String) {
    _uiState.update { state ->
      val updated = state.savedCards.map {
        it.copy(isDefault = it.id == cardId)
      }
      state.copy(
        savedCards = updated,
        feedbackMessage = "Default card updated."
      )
    }
  }

  // Customer Care & Support
  fun openSupportQueryDialog() {
    _uiState.update { it.copy(isSupportQueryDialogOpen = true) }
  }

  fun closeSupportQueryDialog() {
    _uiState.update { it.copy(isSupportQueryDialogOpen = false) }
  }

  fun submitSupportQuery(category: String, message: String) {
    val ticketId = "TKT-${Random.nextInt(1000, 9999)}"
    val newTicket = SupportTicket(
      id = ticketId,
      category = category,
      message = message,
      createdAt = "Just now",
      status = "Open",
      agentResponse = "Thank you for contacting Akram's Support. Our customer executive will reply on WhatsApp and email shortly."
    )
    _uiState.update { state ->
      state.copy(
        supportTickets = listOf(newTicket) + state.supportTickets,
        isSupportQueryDialogOpen = false,
        feedbackMessage = "Support Ticket $ticketId created! We will get back to you shortly."
      )
    }
  }

  fun clearFeedback() {
    _uiState.update { it.copy(feedbackMessage = null) }
  }
}
