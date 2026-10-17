package com.example.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate700

@Composable
fun SupportQueryDialog(
  onDismiss: () -> Unit,
  onSubmitQuery: (category: String, message: String) -> Unit
) {
  var selectedCategory by remember { mutableStateOf("Order Delivery") }
  var messageText by remember { mutableStateOf("") }

  val categories = listOf("Order Delivery", "Warranty / Return", "Payment & Refund", "General Inquiry")

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("support_query_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SupportAgent,
              contentDescription = null,
              tint = BrandBlue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Customer Support",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = BrandNavy
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate700)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Select Topic",
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = Slate700
        )
        Spacer(modifier = Modifier.height(6.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          categories.chunked(2).forEach { rowCats ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              rowCats.forEach { cat ->
                FilterChip(
                  selected = selectedCategory == cat,
                  onClick = { selectedCategory = cat },
                  label = { Text(cat, fontSize = 11.sp) },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = BrandBlue,
                    selectedLabelColor = Color.White
                  ),
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Describe your issue or question",
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = Slate700
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
          value = messageText,
          onValueChange = { messageText = it },
          placeholder = { Text("Enter your message, order number, or product query...", fontSize = 12.sp) },
          minLines = 3,
          maxLines = 5,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("support_message_input")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          TextButton(onClick = onDismiss) {
            Text("Cancel", color = Slate700)
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(
            onClick = {
              if (messageText.isNotBlank()) {
                onSubmitQuery(selectedCategory, messageText)
              }
            },
            enabled = messageText.isNotBlank(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
            modifier = Modifier.testTag("submit_support_query_button")
          ) {
            Text("Submit Ticket", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
