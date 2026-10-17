package com.example.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.CartItem
import com.example.model.PaymentMethod
import com.example.ui.home.getCategoryIcon
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandAmberLight
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueBg
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandRed
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
  cartItems: List<CartItem>,
  selectedPaymentMethod: PaymentMethod,
  subtotal: Int,
  deliveryFee: Int,
  discount: Int,
  totalAmount: Int,
  isPromoApplied: Boolean,
  onDismiss: () -> Unit,
  onUpdateQuantity: (String, String, Int) -> Unit,
  onRemoveItem: (String, String) -> Unit,
  onSelectPaymentMethod: (PaymentMethod) -> Unit,
  onApplyPromoCode: (String) -> Unit,
  onPlaceOrder: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var couponInput by remember { mutableStateOf("") }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 16.dp)
        .testTag("cart_bottom_sheet")
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.ShoppingBag,
            contentDescription = null,
            tint = BrandBlue,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Shopping Cart (${cartItems.sumOf { it.quantity }})",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = BrandNavy
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_cart_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close Cart", tint = Slate700)
        }
      }

      if (cartItems.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Default.ShoppingBag,
              contentDescription = null,
              tint = Slate400,
              modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "Your Cart is Empty",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = BrandNavy
            )
            Text(
              text = "Explore mobile covers, chargers, and earphones starting at ₹299!",
              fontSize = 12.sp,
              color = Slate600,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier
            .weight(1f, fill = false)
            .padding(vertical = 8.dp)
        ) {
          // 1. Items List
          items(cartItems) { item ->
            CartItemRow(
              cartItem = item,
              onIncrease = { onUpdateQuantity(item.product.id, item.selectedColor, 1) },
              onDecrease = { onUpdateQuantity(item.product.id, item.selectedColor, -1) },
              onRemove = { onRemoveItem(item.product.id, item.selectedColor) }
            )
            Divider(color = Slate200, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 8.dp))
          }

          // 2. Delivery Address Preview
          item {
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = Slate100),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.LocationOn,
                  contentDescription = null,
                  tint = BrandBlue,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = "Deliver to ${SampleData.sampleAddress.fullName} (${SampleData.sampleAddress.tag})",
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp,
                      color = BrandNavy
                    )
                  }
                  Text(
                    text = SampleData.sampleAddress.formatted,
                    fontSize = 11.sp,
                    color = Slate600,
                    maxLines = 1
                  )
                }
              }
            }
          }

          // 3. Payment Method & Delivery Fee Choice
          item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Choose Payment & Delivery Mode",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = BrandNavy
            )
            Spacer(modifier = Modifier.height(6.dp))

            // Prepaid UPI
            PaymentMethodRadioOption(
              title = "Prepaid UPI (GPay / PhonePe / Paytm)",
              deliveryBadge = "FREE DELIVERY (₹0)",
              isPrepaid = true,
              isSelected = selectedPaymentMethod == PaymentMethod.PREPAID_UPI,
              onClick = { onSelectPaymentMethod(PaymentMethod.PREPAID_UPI) }
            )

            // Prepaid Card
            PaymentMethodRadioOption(
              title = "Prepaid Credit / Debit Card",
              deliveryBadge = "FREE DELIVERY (₹0)",
              isPrepaid = true,
              isSelected = selectedPaymentMethod == PaymentMethod.PREPAID_CARD,
              onClick = { onSelectPaymentMethod(PaymentMethod.PREPAID_CARD) }
            )

            // Cash on Delivery
            PaymentMethodRadioOption(
              title = "Cash on Delivery (COD)",
              deliveryBadge = "+₹50 Delivery Charge",
              isPrepaid = false,
              isSelected = selectedPaymentMethod == PaymentMethod.CASH_ON_DELIVERY,
              onClick = { onSelectPaymentMethod(PaymentMethod.CASH_ON_DELIVERY) }
            )

            // Dynamic Hint on Savings
            if (selectedPaymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
              Surface(
                color = BrandAmberLight,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp)
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = BrandAmber,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Tip: Pay online via UPI or Card to get FREE delivery and save ₹50!",
                    fontSize = 11.sp,
                    color = BrandAmber,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            } else {
              Surface(
                color = BrandGreenLight,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp)
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BrandGreen,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Awesome! You unlocked 100% FREE Delivery on this prepaid order.",
                    fontSize = 11.sp,
                    color = BrandGreen,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }

          // 4. Promo Code
          item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              OutlinedTextField(
                value = couponInput,
                onValueChange = { couponInput = it },
                placeholder = { Text("Have a coupon? Try AKRAM100", fontSize = 12.sp) },
                singleLine = true,
                modifier = Modifier
                  .weight(1f)
                  .testTag("coupon_input"),
                shape = RoundedCornerShape(8.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = {
                  onApplyPromoCode(couponInput.ifBlank { "AKRAM100" })
                  couponInput = ""
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandNavy),
                modifier = Modifier.testTag("apply_coupon_button")
              ) {
                Text("Apply", fontSize = 12.sp)
              }
            }
          }

          // 5. Price Breakdown
          item {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Slate100),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = "Bill Details (Indian Rupees)",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = BrandNavy
                )
                Spacer(modifier = Modifier.height(8.dp))

                PriceSummaryRow(label = "Items Subtotal", value = "₹$subtotal")

                if (discount > 0) {
                  PriceSummaryRow(
                    label = "Coupon Discount (AKRAM100)",
                    value = "-₹$discount",
                    valueColor = BrandGreen
                  )
                }

                // Delivery Fee Row highlighting FREE vs ₹50
                PriceSummaryRow(
                  label = if (selectedPaymentMethod.isPrepaid) "Delivery Fee (Prepaid)" else "Delivery Fee (Cash on Delivery)",
                  value = if (deliveryFee == 0) "FREE (₹0)" else "₹50",
                  valueColor = if (deliveryFee == 0) BrandGreen else BrandAmber
                )

                Divider(color = Slate200, thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Total Payable",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = BrandNavy
                  )
                  Text(
                    text = "₹$totalAmount",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = BrandNavy
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Place Order Button
        Button(
          onClick = onPlaceOrder,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("cart_place_order_button")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "Pay ₹$totalAmount", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
              Text(
                text = if (selectedPaymentMethod.isPrepaid) "Prepaid • Free Delivery" else "Cash on Delivery (+₹50)",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.85f)
              )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "Place Order", fontWeight = FontWeight.Bold, fontSize = 14.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
private fun CartItemRow(
  cartItem: CartItem,
  onIncrease: () -> Unit,
  onDecrease: () -> Unit,
  onRemove: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Thumbnail
    Box(
      modifier = Modifier
        .size(54.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(BrandBlueBg),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = getCategoryIcon(cartItem.product.category),
        contentDescription = null,
        tint = BrandBlue,
        modifier = Modifier.size(24.dp)
      )
    }

    Spacer(modifier = Modifier.width(10.dp))

    // Product Title & Color
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = cartItem.product.name,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        color = BrandNavy,
        maxLines = 1
      )
      Text(
        text = "Color: ${cartItem.selectedColor} • ₹${cartItem.product.price}",
        fontSize = 11.sp,
        color = Slate600
      )
      Text(
        text = "Total: ₹${cartItem.product.price * cartItem.quantity}",
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = BrandBlue
      )
    }

    // Quantity Stepper
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .background(Slate100, RoundedCornerShape(8.dp))
        .border(1.dp, Slate200, RoundedCornerShape(8.dp))
    ) {
      IconButton(
        onClick = onDecrease,
        modifier = Modifier.size(30.dp)
      ) {
        Icon(
          imageVector = if (cartItem.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
          contentDescription = "Decrease",
          modifier = Modifier.size(14.dp),
          tint = if (cartItem.quantity == 1) BrandRed else Slate700
        )
      }

      Text(
        text = "${cartItem.quantity}",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        modifier = Modifier.padding(horizontal = 6.dp)
      )

      IconButton(
        onClick = onIncrease,
        modifier = Modifier.size(30.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Increase",
          modifier = Modifier.size(14.dp),
          tint = BrandBlue
        )
      }
    }
  }
}

@Composable
private fun PaymentMethodRadioOption(
  title: String,
  deliveryBadge: String,
  isPrepaid: Boolean,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (isSelected) BrandBlueBg else Slate100,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
      .clickable(onClick = onClick)
      .border(
        width = 1.dp,
        color = if (isSelected) BrandBlue else Slate200,
        shape = RoundedCornerShape(10.dp)
      )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        RadioButton(
          selected = isSelected,
          onClick = onClick,
          colors = RadioButtonDefaults.colors(selectedColor = BrandBlue)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = title,
          fontSize = 12.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
          color = BrandNavy
        )
      }

      Surface(
        color = if (isPrepaid) BrandGreen else BrandAmber,
        shape = RoundedCornerShape(6.dp)
      ) {
        Text(
          text = deliveryBadge,
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 10.sp,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
      }
    }
  }
}

@Composable
private fun PriceSummaryRow(
  label: String,
  value: String,
  valueColor: Color = Slate700
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, fontSize = 12.sp, color = Slate600)
    Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
  }
}
