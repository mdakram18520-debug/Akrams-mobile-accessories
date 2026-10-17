package com.example.model

enum class ProductCategory(val title: String) {
  ALL("All"),
  CHARGERS("Chargers"),
  COVERS("Covers"),
  EARPHONES("Earphones"),
  SCREEN_PROTECTORS("Screen Protectors"),
  CABLES("Cables & Adapters"),
  POWER_BANKS("Power Banks")
}

data class Product(
  val id: String,
  val name: String,
  val category: ProductCategory,
  val price: Int, // In Indian Rupees (₹), starting at ₹299
  val originalPrice: Int,
  val rating: Float,
  val reviewCount: Int,
  val description: String,
  val features: List<String>,
  val compatibility: String,
  val inStock: Boolean = true,
  val badge: String? = null,
  val colors: List<String> = listOf("Black", "Navy Blue", "Titanium Grey"),
  val warranty: String = "1 Year Brand Warranty"
) {
  val discountPercent: Int
    get() = if (originalPrice > price) (((originalPrice - price).toFloat() / originalPrice) * 100).toInt() else 0
}

data class CartItem(
  val product: Product,
  val quantity: Int = 1,
  val selectedColor: String = "Black"
)

enum class PaymentMethod(
  val displayName: String,
  val isPrepaid: Boolean,
  val deliveryCharge: Int
) {
  PREPAID_UPI("UPI (GPay / PhonePe / Paytm)", isPrepaid = true, deliveryCharge = 0),
  PREPAID_CARD("Credit / Debit Card", isPrepaid = true, deliveryCharge = 0),
  PREPAID_NETBANKING("Net Banking (All Indian Banks)", isPrepaid = true, deliveryCharge = 0),
  CASH_ON_DELIVERY("Cash on Delivery (COD)", isPrepaid = false, deliveryCharge = 50)
}

data class DeliveryAddress(
  val id: String = "addr_1",
  val fullName: String = "Akram Khan",
  val phoneNumber: String = "+91 97052 67667",
  val addressLine: String = "Flat 402, Royal Palms Residency, 100 Ft Road",
  val area: String = "Indiranagar",
  val city: String = "Bengaluru",
  val state: String = "Karnataka",
  val pincode: String = "560038",
  val tag: String = "Home"
) {
  val formatted: String
    get() = "$addressLine, $area, $city, $state - $pincode"
}

data class SavedCard(
  val id: String,
  val cardNumberMasked: String,
  val cardHolderName: String,
  val expiryDate: String,
  val cardType: String, // "Visa", "Mastercard", "RuPay"
  val bankName: String,
  val isDefault: Boolean = false
)

enum class OrderStatus(val label: String, val stepIndex: Int) {
  PLACED("Order Placed", 0),
  PACKED("Packed at Akram Hub", 1),
  SHIPPED("Shipped", 2),
  OUT_FOR_DELIVERY("Out for Delivery", 3),
  DELIVERED("Delivered", 4)
}

data class TrackingEvent(
  val title: String,
  val location: String,
  val timestamp: String,
  val isCompleted: Boolean,
  val isCurrent: Boolean
)

data class Order(
  val id: String,
  val orderNumber: String,
  val items: List<CartItem>,
  val orderDate: String,
  val expectedDelivery: String,
  val deliveryAddress: DeliveryAddress,
  val paymentMethod: PaymentMethod,
  val subtotal: Int,
  val deliveryFee: Int, // 0 for Prepaid, 50 for COD
  val discount: Int = 0,
  val totalAmount: Int,
  val status: OrderStatus,
  val trackingNumber: String,
  val courierName: String = "Akram Express",
  val trackingEvents: List<TrackingEvent>
)

data class SupportTicket(
  val id: String,
  val category: String,
  val message: String,
  val createdAt: String,
  val status: String = "Open",
  val agentResponse: String? = null
)
