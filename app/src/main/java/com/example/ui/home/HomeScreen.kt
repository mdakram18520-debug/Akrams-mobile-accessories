package com.example.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.ProductCategory
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandAmberLight
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

@Composable
fun HomeScreen(
  products: List<Product>,
  selectedCategory: ProductCategory,
  searchQuery: String,
  onCategorySelected: (ProductCategory) -> Unit,
  onProductClick: (Product) -> Unit,
  onAddToCart: (Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val filteredProducts = products.filter { product ->
    val matchesCategory = (selectedCategory == ProductCategory.ALL || product.category == selectedCategory)
    val matchesSearch = if (searchQuery.isBlank()) true else {
      product.name.contains(searchQuery, ignoreCase = true) ||
          product.category.title.contains(searchQuery, ignoreCase = true) ||
          product.compatibility.contains(searchQuery, ignoreCase = true)
    }
    matchesCategory && matchesSearch
  }

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp)
      .testTag("home_product_grid")
  ) {
    // 1. Promotional Delivery Policy Banner
    item(span = { GridItemSpan(2) }) {
      DeliveryPolicyHeroBanner()
    }

    // 2. Category Quick Filters
    item(span = { GridItemSpan(2) }) {
      CategorySelectorRow(
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected
      )
    }

    // 3. Section Header & Pricing Notice
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = if (selectedCategory == ProductCategory.ALL) "Trending Accessories" else selectedCategory.title,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onBackground
          )
          Text(
            text = "Prices in Indian Rupees (₹) • Starting @ ₹299",
            fontSize = 12.sp,
            color = BrandBlue,
            fontWeight = FontWeight.Medium
          )
        }
        Text(
          text = "${filteredProducts.size} items",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // 4. Products Grid
    if (filteredProducts.isEmpty()) {
      item(span = { GridItemSpan(2) }) {
        EmptySearchResultView(searchQuery = searchQuery)
      }
    } else {
      items(filteredProducts, key = { it.id }) { product ->
        ProductGridCard(
          product = product,
          onCardClick = { onProductClick(product) },
          onAddToCart = { onAddToCart(product) }
        )
      }
    }
  }
}

@Composable
private fun DeliveryPolicyHeroBanner() {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
      .testTag("delivery_policy_banner")
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.linearGradient(
            colors = listOf(BrandNavy, BrandBlue)
          )
        )
        .padding(14.dp)
    ) {
      Column {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .background(Color.White.copy(alpha = 0.2f), CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Akram's Delivery Promise",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }

          Surface(
            color = BrandGreen,
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = "100% FREE",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Free Prepaid Box
          Box(
            modifier = Modifier
              .weight(1f)
              .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
              .padding(8.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = BrandGreenLight,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Prepaid Orders",
                  color = Color.White,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 11.sp
                )
              }
              Text(
                text = "FREE Delivery (₹0)",
                color = BrandGreenLight,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
              )
              Text(
                text = "UPI, Cards & NetBanking",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 10.sp
              )
            }
          }

          // COD Box
          Box(
            modifier = Modifier
              .weight(1f)
              .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
              .padding(8.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.LocalShipping,
                  contentDescription = null,
                  tint = BrandAmber,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Cash on Delivery",
                  color = Color.White,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 11.sp
                )
              }
              Text(
                text = "+₹50 Delivery Fee",
                color = BrandAmberLight,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
              )
              Text(
                text = "Pay cash at doorstep",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 10.sp
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun CategorySelectorRow(
  selectedCategory: ProductCategory,
  onCategorySelected: (ProductCategory) -> Unit
) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(vertical = 4.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    items(ProductCategory.values()) { category ->
      val isSelected = category == selectedCategory
      val icon = getCategoryIcon(category)

      FilterChip(
        selected = isSelected,
        onClick = { onCategorySelected(category) },
        label = {
          Text(
            text = category.title,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp
          )
        },
        leadingIcon = {
          Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
        },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = BrandBlue,
          selectedLabelColor = Color.White,
          selectedLeadingIconColor = Color.White,
          containerColor = Slate100,
          labelColor = Slate700
        ),
        shape = RoundedCornerShape(20.dp),
        border = FilterChipDefaults.filterChipBorder(
          enabled = true,
          selected = isSelected,
          borderColor = if (isSelected) BrandBlue else Slate200,
          selectedBorderColor = BrandBlue
        ),
        modifier = Modifier.testTag("category_chip_${category.name.lowercase()}")
      )
    }
  }
}

fun getCategoryIcon(category: ProductCategory): ImageVector {
  return when (category) {
    ProductCategory.ALL -> Icons.Default.Category
    ProductCategory.CHARGERS -> Icons.Default.Bolt
    ProductCategory.COVERS -> Icons.Default.PhoneAndroid
    ProductCategory.EARPHONES -> Icons.Default.Headphones
    ProductCategory.SCREEN_PROTECTORS -> Icons.Default.Security
    ProductCategory.CABLES -> Icons.Default.Cable
    ProductCategory.POWER_BANKS -> Icons.Default.BatteryChargingFull
  }
}

@Composable
fun ProductGridCard(
  product: Product,
  onCardClick: () -> Unit,
  onAddToCart: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onCardClick)
      .border(1.dp, Slate200, RoundedCornerShape(14.dp))
      .testTag("product_card_${product.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
      // Visual Product Thumbnail Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(110.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(BrandBlueBg),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = getCategoryIcon(product.category),
          contentDescription = product.name,
          tint = BrandBlue,
          modifier = Modifier.size(46.dp)
        )

        // Badge if present
        if (product.badge != null) {
          Surface(
            color = BrandAmber,
            shape = RoundedCornerShape(bottomEnd = 8.dp),
            modifier = Modifier
              .align(Alignment.TopStart)
          ) {
            Text(
              text = product.badge,
              color = Color.White,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        // Discount Tag
        if (product.discountPercent > 0) {
          Surface(
            color = BrandGreen,
            shape = RoundedCornerShape(bottomStart = 8.dp),
            modifier = Modifier.align(Alignment.TopEnd)
          ) {
            Text(
              text = "${product.discountPercent}% OFF",
              color = Color.White,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Rating & Reviews
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Surface(
          color = BrandGreenLight,
          shape = RoundedCornerShape(4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
          ) {
            Text(
              text = "${product.rating}",
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp,
              color = BrandGreen
            )
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = BrandGreen,
              modifier = Modifier.size(10.dp)
            )
          }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "(${product.reviewCount})",
          fontSize = 10.sp,
          color = Slate400
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Product Name
      Text(
        text = product.name,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Price Row in Indian Rupees (₹)
      Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "₹${product.price}",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 16.sp,
          color = BrandNavy
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "₹${product.originalPrice}",
          textDecoration = TextDecoration.LineThrough,
          fontSize = 11.sp,
          color = Slate400
        )
      }

      // Free prepaid delivery pill
      Text(
        text = "⚡ Free Delivery on Prepaid",
        fontSize = 10.sp,
        color = BrandGreen,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 2.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Add to Cart Button
      Button(
        onClick = onAddToCart,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = BrandBlue,
          contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(34.dp)
          .testTag("add_to_cart_${product.id}")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Add to Cart",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}

@Composable
private fun EmptySearchResultView(searchQuery: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(40.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        imageVector = Icons.Default.PhoneAndroid,
        contentDescription = null,
        tint = Slate400,
        modifier = Modifier.size(48.dp)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "No products found for \"$searchQuery\"",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = Slate700
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Try searching for 'Chargers', 'Covers', 'Earphones', or browse all categories.",
        fontSize = 12.sp,
        color = Slate600
      )
    }
  }
}
