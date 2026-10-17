package com.example

import com.example.data.SampleData
import com.example.model.PaymentMethod
import com.example.model.ProductCategory
import com.example.viewmodel.ShopViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testProductCatalogPricing_allProductsAtLeast299() {
    val products = SampleData.products
    assertTrue("Product catalog should not be empty", products.isNotEmpty())

    // All products must be priced in Indian Rupees starting at ₹299
    products.forEach { product ->
      assertTrue(
        "Product ${product.name} price ₹${product.price} should be >= 299",
        product.price >= 299
      )
    }

    // Verify key categories exist
    val categories = products.map { it.category }.toSet()
    assertTrue(categories.contains(ProductCategory.CHARGERS))
    assertTrue(categories.contains(ProductCategory.COVERS))
    assertTrue(categories.contains(ProductCategory.EARPHONES))
  }

  @Test
  fun testDeliveryCharges_freePrepaidAndFiftyCod() {
    assertEquals(0, PaymentMethod.PREPAID_UPI.deliveryCharge)
    assertEquals(0, PaymentMethod.PREPAID_CARD.deliveryCharge)
    assertEquals(0, PaymentMethod.PREPAID_NETBANKING.deliveryCharge)
    assertEquals(50, PaymentMethod.CASH_ON_DELIVERY.deliveryCharge)
  }

  @Test
  fun testCartCalculation_withPrepaidAndCodDelivery() {
    val viewModel = ShopViewModel()
    val product1 = viewModel.allProducts.first { it.price >= 299 }

    viewModel.addToCart(product1)

    // Default payment method is PREPAID_UPI -> Free delivery (0)
    assertEquals(PaymentMethod.PREPAID_UPI, viewModel.uiState.value.selectedPaymentMethod)
    assertEquals(0, viewModel.uiState.value.deliveryFee)
    assertEquals(product1.price, viewModel.uiState.value.totalAmount)

    // Switch to Cash on Delivery -> ₹50 delivery charge applied
    viewModel.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY)
    assertEquals(50, viewModel.uiState.value.deliveryFee)
    assertEquals(product1.price + 50, viewModel.uiState.value.totalAmount)

    // Switch back to Prepaid Card -> Free delivery (0)
    viewModel.setPaymentMethod(PaymentMethod.PREPAID_CARD)
    assertEquals(0, viewModel.uiState.value.deliveryFee)
    assertEquals(product1.price, viewModel.uiState.value.totalAmount)
  }

  @Test
  fun testPlaceOrder_generatesOrderWithTracking() {
    val viewModel = ShopViewModel()
    val product = viewModel.allProducts[0]

    viewModel.addToCart(product)
    viewModel.setPaymentMethod(PaymentMethod.PREPAID_UPI)

    val initialOrdersCount = viewModel.uiState.value.orders.size
    val placedOrder = viewModel.placeOrder()

    assertNotNull(placedOrder)
    assertEquals(initialOrdersCount + 1, viewModel.uiState.value.orders.size)
    assertEquals(0, viewModel.uiState.value.cartItems.size)
    assertTrue(placedOrder!!.orderNumber.startsWith("AKM-"))
    assertTrue(placedOrder.trackingEvents.isNotEmpty())
    assertEquals(0, placedOrder.deliveryFee)
  }

  @Test
  fun testCardManagement_addAndSetDefault() {
    val viewModel = ShopViewModel()
    val initialCardCount = viewModel.uiState.value.savedCards.size

    viewModel.addNewCard(
      cardNumber = "4111 2222 3333 4444",
      holderName = "AKRAM KHAN",
      expiry = "12/29",
      cardType = "Visa",
      bankName = "Axis Bank"
    )

    assertEquals(initialCardCount + 1, viewModel.uiState.value.savedCards.size)
    val addedCard = viewModel.uiState.value.savedCards.last()
    assertTrue(addedCard.cardNumberMasked.endsWith("4444"))

    viewModel.setDefaultCard(addedCard.id)
    val defaultCard = viewModel.uiState.value.savedCards.first { it.id == addedCard.id }
    assertTrue(defaultCard.isDefault)
  }
}
