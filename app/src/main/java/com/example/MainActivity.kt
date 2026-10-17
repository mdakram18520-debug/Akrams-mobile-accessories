package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.account.AccountScreen
import com.example.ui.account.AddCardDialog
import com.example.ui.account.OrderTrackingDialog
import com.example.ui.account.SupportQueryDialog
import com.example.ui.cart.CartBottomSheet
import com.example.ui.components.AkramBottomBar
import com.example.ui.components.AkramTopBar
import com.example.ui.home.HomeScreen
import com.example.ui.home.ProductDetailDialog
import com.example.ui.order.OrderSuccessDialog
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppTab
import com.example.viewmodel.ShopViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        AkramMobileHubApp()
      }
    }
  }
}

@Composable
fun AkramMobileHubApp(
  viewModel: ShopViewModel = viewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  // Show feedback messages
  LaunchedEffect(uiState.feedbackMessage) {
    uiState.feedbackMessage?.let { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
      viewModel.clearFeedback()
    }
  }

  // Handle Hardware / Predictive Back
  BackHandler(
    enabled = uiState.selectedProductForDetail != null ||
        uiState.isCartSheetOpen ||
        uiState.selectedOrderForTracking != null ||
        uiState.isAddCardDialogOpen ||
        uiState.isSupportQueryDialogOpen ||
        uiState.currentTab != AppTab.HOME ||
        uiState.searchQuery.isNotEmpty()
  ) {
    when {
      uiState.selectedProductForDetail != null -> viewModel.closeProductDetail()
      uiState.isCartSheetOpen -> viewModel.closeCart()
      uiState.selectedOrderForTracking != null -> viewModel.closeOrderTracking()
      uiState.isAddCardDialogOpen -> viewModel.closeAddCardDialog()
      uiState.isSupportQueryDialogOpen -> viewModel.closeSupportQueryDialog()
      uiState.searchQuery.isNotEmpty() -> viewModel.setSearchQuery("")
      uiState.currentTab != AppTab.HOME -> viewModel.setTab(AppTab.HOME)
    }
  }

  Scaffold(
    topBar = {
      AkramTopBar(
        searchQuery = uiState.searchQuery,
        onSearchChange = { viewModel.setSearchQuery(it) },
        cartItemCount = uiState.cartItemCount,
        onCartClick = { viewModel.openCart() },
        isHomeScreen = uiState.currentTab == AppTab.HOME
      )
    },
    bottomBar = {
      AkramBottomBar(
        currentTab = uiState.currentTab,
        onTabSelected = { viewModel.setTab(it) },
        activeOrdersCount = uiState.activeOrdersCount
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Crossfade(
      targetState = uiState.currentTab,
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      label = "screen_crossfade"
    ) { tab ->
      when (tab) {
        AppTab.HOME -> {
          HomeScreen(
            products = viewModel.allProducts,
            selectedCategory = uiState.selectedCategory,
            searchQuery = uiState.searchQuery,
            onCategorySelected = { viewModel.setCategory(it) },
            onProductClick = { viewModel.openProductDetail(it) },
            onAddToCart = { viewModel.addToCart(it) }
          )
        }

        AppTab.ACCOUNT -> {
          AccountScreen(
            orders = uiState.orders,
            savedCards = uiState.savedCards,
            supportTickets = uiState.supportTickets,
            onTrackOrder = { viewModel.openOrderTracking(it) },
            onOpenAddCard = { viewModel.openAddCardDialog() },
            onDeleteCard = { viewModel.deleteCard(it) },
            onSetDefaultCard = { viewModel.setDefaultCard(it) },
            onOpenSupportQuery = { viewModel.openSupportQueryDialog() }
          )
        }
      }
    }
  }

  // Modals & Bottom Sheets

  // 1. Product Detail Sheet
  uiState.selectedProductForDetail?.let { product ->
    ProductDetailDialog(
      product = product,
      onDismiss = { viewModel.closeProductDetail() },
      onAddToCart = { prod, color ->
        viewModel.addToCart(prod, color)
        viewModel.closeProductDetail()
      },
      onBuyNow = { prod, color ->
        viewModel.addToCart(prod, color)
        viewModel.closeProductDetail()
        viewModel.openCart()
      }
    )
  }

  // 2. Cart & Checkout Bottom Sheet
  if (uiState.isCartSheetOpen) {
    CartBottomSheet(
      cartItems = uiState.cartItems,
      selectedPaymentMethod = uiState.selectedPaymentMethod,
      subtotal = uiState.subtotal,
      deliveryFee = uiState.deliveryFee,
      discount = uiState.discount,
      totalAmount = uiState.totalAmount,
      isPromoApplied = uiState.isPromoApplied,
      onDismiss = { viewModel.closeCart() },
      onUpdateQuantity = { prodId, color, delta ->
        viewModel.updateCartQuantity(prodId, color, delta)
      },
      onRemoveItem = { prodId, color ->
        viewModel.removeFromCart(prodId, color)
      },
      onSelectPaymentMethod = { viewModel.setPaymentMethod(it) },
      onApplyPromoCode = { viewModel.applyPromoCode(it) },
      onPlaceOrder = { viewModel.placeOrder() }
    )
  }

  // 3. Order Tracking Sheet
  uiState.selectedOrderForTracking?.let { order ->
    OrderTrackingDialog(
      order = order,
      onDismiss = { viewModel.closeOrderTracking() }
    )
  }

  // 4. Add Card Dialog
  if (uiState.isAddCardDialogOpen) {
    AddCardDialog(
      onDismiss = { viewModel.closeAddCardDialog() },
      onAddCard = { number, name, expiry, type, bank ->
        viewModel.addNewCard(number, name, expiry, type, bank)
      }
    )
  }

  // 5. Support Query Dialog
  if (uiState.isSupportQueryDialogOpen) {
    SupportQueryDialog(
      onDismiss = { viewModel.closeSupportQueryDialog() },
      onSubmitQuery = { category, message ->
        viewModel.submitSupportQuery(category, message)
      }
    )
  }

  // 6. Order Success Confirmation Dialog
  uiState.lastPlacedOrder?.let { order ->
    OrderSuccessDialog(
      order = order,
      onTrackOrder = {
        viewModel.clearLastPlacedOrder()
        viewModel.setTab(AppTab.ACCOUNT)
        viewModel.openOrderTracking(it)
      },
      onContinueShopping = {
        viewModel.clearLastPlacedOrder()
      }
    )
  }
}
