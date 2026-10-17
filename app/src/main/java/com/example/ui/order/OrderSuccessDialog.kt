package com.example.ui.order

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Order
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700

@Composable
fun OrderSuccessDialog(
  order: Order,
  onTrackOrder: (Order) -> Unit,
  onContinueShopping: () -> Unit
) {
  Dialog(onDismissRequest = onContinueShopping) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("order_success_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Success Icon
        Box(
          modifier = Modifier
            .size(64.dp)
            .background(BrandGreenLight, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = BrandGreen,
            modifier = Modifier.size(38.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Order Placed Successfully!",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 18.sp,
          color = BrandNavy,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Order #${order.orderNumber} is confirmed and will be packed at Akram Hub.",
          fontSize = 12.sp,
          color = Slate600,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Order Summary Card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Slate100),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Amount Payable", fontSize = 12.sp, color = Slate600)
              Text(
                text = "₹${order.totalAmount}",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BrandNavy
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Payment Mode", fontSize = 12.sp, color = Slate600)
              Text(
                text = order.paymentMethod.displayName,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandNavy
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Delivery Fee", fontSize = 12.sp, color = Slate600)
              Text(
                text = if (order.deliveryFee == 0) "FREE (₹0)" else "₹50 (COD Fee)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (order.deliveryFee == 0) BrandGreen else BrandAmber
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Expected Delivery", fontSize = 12.sp, color = Slate600)
              Text(
                text = order.expectedDelivery,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = BrandBlue
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Track Order Button
        Button(
          onClick = { onTrackOrder(order) },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("success_track_order_button")
        ) {
          Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Track My Order", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
          onClick = onContinueShopping,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("success_continue_shopping_button")
        ) {
          Text(text = "Continue Shopping", color = Slate700, fontWeight = FontWeight.Medium)
        }
      }
    }
  }
}
