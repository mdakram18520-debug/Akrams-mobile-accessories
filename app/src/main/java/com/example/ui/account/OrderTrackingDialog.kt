package com.example.ui.account

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueBg
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingDialog(
  order: Order,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val clipboardManager = LocalClipboardManager.current

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 16.dp)
        .testTag("order_tracking_sheet")
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Order #${order.orderNumber}",
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp,
              color = BrandNavy
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
              color = if (order.status == OrderStatus.DELIVERED) BrandGreenLight else BrandBlueBg,
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = order.status.label,
                color = if (order.status == OrderStatus.DELIVERED) BrandGreen else BrandBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }
          Text(
            text = "Expected: ${order.expectedDelivery}",
            fontSize = 12.sp,
            color = Slate600
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_tracking_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate700)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Courier & Tracking Info Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Slate100),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = BrandBlue,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = order.courierName,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = BrandNavy
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = order.trackingNumber,
                fontSize = 11.sp,
                color = Slate600,
                fontWeight = FontWeight.Medium
              )
              IconButton(
                onClick = { clipboardManager.setText(AnnotatedString(order.trackingNumber)) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.ContentCopy,
                  contentDescription = "Copy Tracking ID",
                  modifier = Modifier.size(14.dp),
                  tint = BrandBlue
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Live Tracking Timeline
      Text(
        text = "Delivery Progress",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = BrandNavy
      )
      Spacer(modifier = Modifier.height(12.dp))

      order.trackingEvents.forEachIndexed { index, event ->
        Row(modifier = Modifier.fillMaxWidth()) {
          // Timeline Node & Vertical Line
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .background(
                  color = if (event.isCurrent) BrandBlue else if (event.isCompleted) BrandGreen else Slate200,
                  shape = CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              if (event.isCompleted) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(14.dp)
                )
              }
            }

            if (index < order.trackingEvents.size - 1) {
              Box(
                modifier = Modifier
                  .width(2.dp)
                  .height(44.dp)
                  .background(if (event.isCompleted) BrandGreen else Slate200)
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          // Event details
          Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
              text = event.title,
              fontWeight = if (event.isCurrent) FontWeight.Bold else FontWeight.SemiBold,
              fontSize = 13.sp,
              color = if (event.isCurrent) BrandBlue else BrandNavy
            )
            Text(
              text = event.location,
              fontSize = 11.sp,
              color = Slate600
            )
            Text(
              text = event.timestamp,
              fontSize = 10.sp,
              color = Slate400
            )
          }
        }
      }

      // Delivery Address
      Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Slate100),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = BrandBlue,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "Delivering to: ${order.deliveryAddress.fullName}",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = BrandNavy
            )
            Text(
              text = order.deliveryAddress.formatted,
              fontSize = 11.sp,
              color = Slate600
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Items Summary & Paid Amount
      Text(
        text = "Items in this Order",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = BrandNavy
      )
      Spacer(modifier = Modifier.height(8.dp))

      order.items.forEach { item ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "${item.quantity}x ${item.product.name}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = BrandNavy
            )
            Text(
              text = "Color: ${item.selectedColor}",
              fontSize = 10.sp,
              color = Slate600
            )
          }
          Text(
            text = "₹${item.product.price * item.quantity}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = BrandNavy
          )
        }
      }

      Divider(color = Slate200, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

      // Payment Breakdown
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "Payment Mode", fontSize = 12.sp, color = Slate600)
        Text(text = order.paymentMethod.displayName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = BrandNavy)
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "Delivery Charge", fontSize = 12.sp, color = Slate600)
        Text(
          text = if (order.deliveryFee == 0) "FREE (₹0)" else "₹50 (COD Fee)",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = if (order.deliveryFee == 0) BrandGreen else BrandAmber
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "Total Paid", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BrandNavy)
        Text(text = "₹${order.totalAmount}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = BrandNavy)
      }

      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
