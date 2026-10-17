package com.example.ui.account

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.model.SavedCard
import com.example.model.SupportTicket
import com.example.ui.theme.BlueGradient1
import com.example.ui.theme.BlueGradient2
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandAmberLight
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueBg
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandRed
import com.example.ui.theme.CardGradient1
import com.example.ui.theme.CardGradient2
import com.example.ui.theme.GoldGradient1
import com.example.ui.theme.GoldGradient2
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700

@Composable
fun AccountScreen(
  orders: List<Order>,
  savedCards: List<SavedCard>,
  supportTickets: List<SupportTicket>,
  onTrackOrder: (Order) -> Unit,
  onOpenAddCard: () -> Unit,
  onDeleteCard: (String) -> Unit,
  onSetDefaultCard: (String) -> Unit,
  onOpenSupportQuery: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  val accountTabs = listOf("Order Tracking", "Card Management", "Customer Support")

  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  LazyColumn(
    contentPadding = PaddingValues(bottom = 80.dp),
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 14.dp)
      .testTag("account_screen_root")
  ) {
    // 1. User VIP Profile Header Card
    item {
      ProfileHeaderCard()
    }

    // 2. Segmented Navigation Tabs
    item {
      Spacer(modifier = Modifier.height(14.dp))
      ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = BrandBlue,
        edgePadding = 0.dp,
        divider = {},
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
            color = BrandBlue,
            height = 3.dp
          )
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        accountTabs.forEachIndexed { index, title ->
          val isSelected = selectedTabIndex == index
          Tab(
            selected = isSelected,
            onClick = { selectedTabIndex = index },
            text = {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = title,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontSize = 13.sp,
                  color = if (isSelected) BrandBlue else Slate600
                )
                if (index == 0 && orders.count { it.status != OrderStatus.DELIVERED } > 0) {
                  Spacer(modifier = Modifier.width(4.dp))
                  Surface(
                    color = BrandAmber,
                    shape = CircleShape
                  ) {
                    Text(
                      text = "${orders.count { it.status != OrderStatus.DELIVERED }}",
                      color = Color.White,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                  }
                }
              }
            },
            modifier = Modifier.testTag("account_tab_$index")
          )
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // 3. Tab Content
    when (selectedTabIndex) {
      0 -> {
        // ORDER TRACKING SECTION
        if (orders.isEmpty()) {
          item {
            EmptyOrdersView()
          }
        } else {
          items(orders, key = { it.id }) { order ->
            OrderSummaryCard(
              order = order,
              onTrackClick = { onTrackOrder(order) }
            )
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
      }

      1 -> {
        // CARD MANAGEMENT SECTION
        item {
          CardManagementHeader(onAddNewCard = onOpenAddCard)
        }

        if (savedCards.isEmpty()) {
          item {
            EmptyCardsView(onAddNewCard = onOpenAddCard)
          }
        } else {
          items(savedCards, key = { it.id }) { card ->
            SavedCreditCardItem(
              card = card,
              onSetDefault = { onSetDefaultCard(card.id) },
              onDelete = { onDeleteCard(card.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
      }

      2 -> {
        // CUSTOMER CARE & SUPPORT SECTION
        item {
          CustomerSupportSection(
            context = context,
            clipboardManager = clipboardManager,
            supportTickets = supportTickets,
            onOpenSupportQuery = onOpenSupportQuery
          )
        }
      }
    }
  }
}

@Composable
private fun ProfileHeaderCard() {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 10.dp)
      .border(1.dp, Slate200, RoundedCornerShape(16.dp))
      .testTag("account_profile_card")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Avatar with Initials
        Box(
          modifier = Modifier
            .size(52.dp)
            .background(BrandNavy, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "AK",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Akram Khan",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = BrandNavy
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              color = BrandAmberLight,
              shape = RoundedCornerShape(6.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = BrandAmber, modifier = Modifier.size(10.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = "VIP Member",
                  color = BrandAmber,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Text(
            text = "+91 97052 67667 • akram@example.com",
            fontSize = 12.sp,
            color = Slate600
          )
          Text(
            text = "⚡ Free Prepaid Delivery Active across India",
            fontSize = 11.sp,
            color = BrandGreen,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
private fun OrderSummaryCard(
  order: Order,
  onTrackClick: () -> Unit
) {
  val isDelivered = order.status == OrderStatus.DELIVERED

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, Slate200, RoundedCornerShape(14.dp))
      .testTag("order_card_${order.orderNumber}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Header: Order #, Status chip, Date
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Order #${order.orderNumber}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = BrandNavy
          )
          Text(
            text = "Placed on ${order.orderDate}",
            fontSize = 11.sp,
            color = Slate400
          )
        }

        Surface(
          color = if (isDelivered) BrandGreenLight else BrandBlueBg,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = order.status.label,
            color = if (isDelivered) BrandGreen else BrandBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Products preview
      order.items.forEach { item ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "${item.quantity}x ${item.product.name}",
            fontSize = 12.sp,
            color = Slate700,
            maxLines = 1,
            modifier = Modifier.weight(1f)
          )
          Text(
            text = "₹${item.product.price * item.quantity}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = BrandNavy
          )
        }
      }

      Divider(color = Slate200, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 8.dp))

      // Delivery & Total Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Total: ₹${order.totalAmount}",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp,
              color = BrandNavy
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              color = if (order.deliveryFee == 0) BrandGreenLight else BrandAmberLight,
              shape = RoundedCornerShape(4.dp)
            ) {
              Text(
                text = if (order.deliveryFee == 0) "Free Delivery" else "+₹50 COD",
                color = if (order.deliveryFee == 0) BrandGreen else BrandAmber,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
              )
            }
          }
          Text(
            text = "${order.courierName} • ${order.trackingNumber}",
            fontSize = 10.sp,
            color = Slate400
          )
        }

        Button(
          onClick = onTrackClick,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("track_button_${order.orderNumber}")
        ) {
          Icon(
            imageVector = Icons.Default.LocalShipping,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = "Track Order", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun CardManagementHeader(onAddNewCard: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Text(
        text = "Saved Debit & Credit Cards",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = BrandNavy
      )
      Text(
        text = "Fast 1-click checkout with Free Prepaid Delivery",
        fontSize = 11.sp,
        color = Slate600
      )
    }

    Button(
      onClick = onAddNewCard,
      shape = RoundedCornerShape(8.dp),
      colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
      contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
      modifier = Modifier.testTag("add_new_card_button")
    ) {
      Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
      Spacer(modifier = Modifier.width(4.dp))
      Text("Add Card", fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
private fun SavedCreditCardItem(
  card: SavedCard,
  onSetDefault: () -> Unit,
  onDelete: () -> Unit
) {
  val gradient = when (card.cardType) {
    "Visa" -> listOf(BlueGradient1, BlueGradient2)
    "RuPay" -> listOf(GoldGradient1, GoldGradient2)
    else -> listOf(CardGradient1, CardGradient2)
  }

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_item_${card.id}")
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(Brush.linearGradient(gradient))
        .padding(18.dp)
    ) {
      Column {
        // Bank Name & Network
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = card.bankName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
          Surface(
            color = Color.White.copy(alpha = 0.2f),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = card.cardType.uppercase(),
              color = Color.White,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // EMV Chip Icon + Masked Number
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(width = 30.dp, height = 22.dp)
              .background(Color(0xFFFFD700), RoundedCornerShape(4.dp))
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = card.cardNumberMasked,
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            letterSpacing = 1.sp
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cardholder & Expiry
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Bottom
        ) {
          Column {
            Text(
              text = "CARDHOLDER",
              fontSize = 9.sp,
              color = Color.White.copy(alpha = 0.7f),
              fontWeight = FontWeight.Medium
            )
            Text(
              text = card.cardHolderName,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "EXPIRES",
              fontSize = 9.sp,
              color = Color.White.copy(alpha = 0.7f),
              fontWeight = FontWeight.Medium
            )
            Text(
              text = card.expiryDate,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = Color.White.copy(alpha = 0.2f), thickness = 0.8.dp)
        Spacer(modifier = Modifier.height(6.dp))

        // Card action bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (card.isDefault) {
            Surface(
              color = BrandGreen,
              shape = RoundedCornerShape(12.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Default Payment Card",
                  color = Color.White,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          } else {
            TextButton(
              onClick = onSetDefault,
              contentPadding = PaddingValues(0.dp)
            ) {
              Text(
                text = "Set as Default",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          IconButton(
            onClick = onDelete,
            modifier = Modifier.size(28.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Delete Card",
              tint = Color.White.copy(alpha = 0.85f),
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun CustomerSupportSection(
  context: Context,
  clipboardManager: androidx.compose.ui.platform.ClipboardManager,
  supportTickets: List<SupportTicket>,
  onOpenSupportQuery: () -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    // 1. Support Banner Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Slate200, RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .background(BrandBlueBg, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.SupportAgent,
              contentDescription = null,
              tint = BrandBlue,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "Akram's Customer Care",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = BrandNavy
            )
            Text(
              text = "We are here to assist you 7 days a week",
              fontSize = 11.sp,
              color = Slate600
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick Contact Actions: Call, WhatsApp, Email
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Call Button
          ContactActionButton(
            icon = Icons.Default.Call,
            title = "Call Care",
            subtitle = "+91 97052 67667",
            iconColor = BrandGreen,
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+919705267667")
              }
              try { context.startActivity(intent) } catch (e: Exception) {
                Toast.makeText(context, "Dialing +91 97052 67667", Toast.LENGTH_SHORT).show()
              }
            },
            modifier = Modifier.weight(1f)
          )

          // WhatsApp Button
          ContactActionButton(
            icon = Icons.Default.Chat,
            title = "WhatsApp",
            subtitle = "Instant Chat",
            iconColor = BrandGreen,
            onClick = {
              val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/919705267667?text=Hello%20Akram's%20Mobile%20Hub%20Support")
              }
              try { context.startActivity(intent) } catch (e: Exception) {
                Toast.makeText(context, "Opening WhatsApp for +91 97052 67667", Toast.LENGTH_SHORT).show()
              }
            },
            modifier = Modifier.weight(1f)
          )

          // Email Button
          ContactActionButton(
            icon = Icons.Default.Email,
            title = "Email Care",
            subtitle = "24hr Reply",
            iconColor = BrandBlue,
            onClick = {
              val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:care@akramsmobilehub.in?subject=Support%20Request%20-%20Akram's%20Mobile%20Hub")
              }
              try { context.startActivity(intent) } catch (e: Exception) {
                Toast.makeText(context, "Opening Email to care@akramsmobilehub.in", Toast.LENGTH_SHORT).show()
              }
            },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Operating Hours & Hub Address
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Slate100),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = SampleData.customerCare.operatingHours,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandNavy
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.Top) {
              Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = BrandAmber, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Main Store & Dispatch Hub:",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Slate700
                )
                Text(
                  text = SampleData.customerCare.storeAddress,
                  fontSize = 10.sp,
                  color = Slate600
                )
              }
              IconButton(
                onClick = {
                  clipboardManager.setText(AnnotatedString(SampleData.customerCare.storeAddress))
                  Toast.makeText(context, "Hub address copied to clipboard!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy Address", modifier = Modifier.size(14.dp), tint = BrandBlue)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Raise Ticket Button
        Button(
          onClick = onOpenSupportQuery,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("raise_support_ticket_button")
        ) {
          Icon(imageVector = Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Send Support Message / Raise Ticket", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
      }
    }

    // 2. User's Recent Support Tickets
    if (supportTickets.isNotEmpty()) {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "Your Support Inquiries",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = BrandNavy
      )
      Spacer(modifier = Modifier.height(8.dp))

      supportTickets.forEach { ticket ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(1.dp, Slate200, RoundedCornerShape(12.dp))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${ticket.id} • ${ticket.category}",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = BrandNavy
              )
              Surface(
                color = if (ticket.status == "Resolved") BrandGreenLight else BrandAmberLight,
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = ticket.status,
                  color = if (ticket.status == "Resolved") BrandGreen else BrandAmber,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Query: ${ticket.message}", fontSize = 11.sp, color = Slate700)

            if (ticket.agentResponse != null) {
              Spacer(modifier = Modifier.height(6.dp))
              Surface(
                color = BrandBlueBg,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(modifier = Modifier.padding(8.dp)) {
                  Text(text = "Support Reply: ", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = BrandBlue)
                  Text(text = ticket.agentResponse, fontSize = 11.sp, color = Slate700)
                }
              }
            }
          }
        }
      }
    }

    // 3. Frequently Asked Questions (FAQ)
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Help & Policies FAQ",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = BrandNavy
    )
    Spacer(modifier = Modifier.height(8.dp))

    SampleData.faqs.forEach { (question, answer) ->
      FaqExpandableCard(question = question, answer = answer)
      Spacer(modifier = Modifier.height(6.dp))
    }
  }
}

@Composable
private fun ContactActionButton(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  iconColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = Slate100,
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .clickable(onClick = onClick)
      .border(1.dp, Slate200, RoundedCornerShape(10.dp))
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .background(iconColor.copy(alpha = 0.15f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = BrandNavy)
      Text(text = subtitle, fontSize = 9.sp, color = Slate600)
    }
  }
}

@Composable
private fun FaqExpandableCard(question: String, answer: String) {
  var isExpanded by remember { mutableStateOf(false) }

  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, Slate200, RoundedCornerShape(10.dp))
      .clickable { isExpanded = !isExpanded }
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = question,
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = BrandNavy,
          modifier = Modifier.weight(1f)
        )
        Icon(
          imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = Slate600,
          modifier = Modifier.size(18.dp)
        )
      }

      AnimatedVisibility(visible = isExpanded) {
        Column {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = answer,
            fontSize = 11.sp,
            color = Slate600,
            lineHeight = 16.sp
          )
        }
      }
    }
  }
}

@Composable
private fun EmptyOrdersView() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(40.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = Slate400, modifier = Modifier.size(48.dp))
      Spacer(modifier = Modifier.height(8.dp))
      Text("No Orders Yet", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BrandNavy)
      Text("Your placed orders will show real-time tracking here.", fontSize = 12.sp, color = Slate600)
    }
  }
}

@Composable
private fun EmptyCardsView(onAddNewCard: () -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, tint = Slate400, modifier = Modifier.size(48.dp))
      Spacer(modifier = Modifier.height(8.dp))
      Text("No Saved Cards", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BrandNavy)
      Text("Save a Visa, Mastercard, or RuPay card for instant checkout with free delivery.", fontSize = 11.sp, color = Slate600)
      Spacer(modifier = Modifier.height(12.dp))
      Button(onClick = onAddNewCard, colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)) {
        Text("Add New Card")
      }
    }
  }
}
