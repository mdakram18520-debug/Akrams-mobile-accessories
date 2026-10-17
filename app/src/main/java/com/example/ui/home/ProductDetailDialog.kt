package com.example.ui.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueBg
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailDialog(
  product: Product,
  onDismiss: () -> Unit,
  onAddToCart: (Product, String) -> Unit,
  onBuyNow: (Product, String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var selectedColor by remember { mutableStateOf(product.colors.firstOrNull() ?: "Standard") }

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
        .testTag("product_detail_sheet")
    ) {
      // Header with close button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = BrandBlueBg,
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = product.category.title,
            color = BrandBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_product_detail_button")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Detail Sheet",
            tint = Slate700
          )
        }
      }

      // Large Preview Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(BrandBlueBg),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = getCategoryIcon(product.category),
          contentDescription = product.name,
          tint = BrandBlue,
          modifier = Modifier.size(72.dp)
        )

        if (product.badge != null) {
          Surface(
            color = BrandAmber,
            shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(8.dp)
          ) {
            Text(
              text = product.badge,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Product Title
      Text(
        text = product.name,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = BrandNavy
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Pricing & Discount in Indian Rupees (₹)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "₹${product.price}",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 24.sp,
          color = BrandNavy
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "₹${product.originalPrice}",
          fontSize = 15.sp,
          textDecoration = TextDecoration.LineThrough,
          color = Slate400
        )
        Spacer(modifier = Modifier.width(10.dp))
        Surface(
          color = BrandGreenLight,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "${product.discountPercent}% OFF",
            color = BrandGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Delivery Charges Breakdown Box
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Slate100),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocalShipping,
              contentDescription = null,
              tint = BrandBlue,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Akram Express Shipping Options",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = BrandNavy
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "• Prepaid (UPI / Cards / NetBanking):",
              fontSize = 12.sp,
              color = Slate700
            )
            Text(
              text = "FREE (₹0)",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = BrandGreen
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "• Cash on Delivery (COD):",
              fontSize = 12.sp,
              color = Slate700
            )
            Text(
              text = "+₹50 Delivery Fee",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = BrandAmber
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Colors Selection
      if (product.colors.isNotEmpty()) {
        Text(
          text = "Select Color: $selectedColor",
          fontWeight = FontWeight.SemiBold,
          fontSize = 13.sp,
          color = BrandNavy
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          product.colors.forEach { colorName ->
            val isSelected = colorName == selectedColor
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) BrandBlue else Slate100,
              modifier = Modifier
                .clickable { selectedColor = colorName }
                .border(
                  width = 1.dp,
                  color = if (isSelected) BrandBlue else Slate200,
                  shape = RoundedCornerShape(8.dp)
                )
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                if (isSelected) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                  text = colorName,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) Color.White else Slate700
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Description
      Text(
        text = "Product Overview",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = BrandNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = product.description,
        fontSize = 13.sp,
        color = Slate700,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Key Features
      Text(
        text = "Key Features",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = BrandNavy
      )
      Spacer(modifier = Modifier.height(6.dp))
      product.features.forEach { feature ->
        Row(
          modifier = Modifier.padding(vertical = 2.dp),
          verticalAlignment = Alignment.Top
        ) {
          Text(text = "✓", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = feature, fontSize = 12.sp, color = Slate700)
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Compatibility & Warranty
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Card(
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Slate100),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Compatibility", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = BrandNavy)
            Text(text = product.compatibility, fontSize = 10.sp, color = Slate700, maxLines = 2)
          }
        }
        Card(
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Slate100),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Warranty", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = BrandNavy)
            Text(text = product.warranty, fontSize = 10.sp, color = Slate700)
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = { onAddToCart(product, selectedColor) },
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("detail_add_to_cart_button")
        ) {
          Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Add to Cart", fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = { onBuyNow(product, selectedColor) },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("detail_buy_now_button")
        ) {
          Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Buy Now", fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
