package com.example.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700

@Composable
fun AddCardDialog(
  onDismiss: () -> Unit,
  onAddCard: (cardNumber: String, holderName: String, expiry: String, cardType: String, bankName: String) -> Unit
) {
  var cardNumber by remember { mutableStateOf("") }
  var holderName by remember { mutableStateOf("") }
  var expiry by remember { mutableStateOf("") }
  var cvv by remember { mutableStateOf("") }
  var bankName by remember { mutableStateOf("HDFC Bank") }
  var cardType by remember { mutableStateOf("Visa") }

  val isFormValid = cardNumber.replace(" ", "").length >= 16 &&
      holderName.trim().isNotBlank() &&
      expiry.trim().length >= 4

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("add_card_dialog")
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
              imageVector = Icons.Default.CreditCard,
              contentDescription = null,
              tint = BrandBlue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Add New Card",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = BrandNavy
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate700)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card Type selector
        Text(text = "Card Network", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
        Spacer(modifier = Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          listOf("Visa", "Mastercard", "RuPay").forEach { type ->
            FilterChip(
              selected = cardType == type,
              onClick = { cardType = type },
              label = { Text(type, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BrandBlue,
                selectedLabelColor = Color.White
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Card Number
        OutlinedTextField(
          value = cardNumber,
          onValueChange = { input ->
            val digits = input.filter { it.isDigit() }.take(16)
            cardNumber = digits.chunked(4).joinToString(" ")
          },
          label = { Text("Card Number (16 Digits)") },
          placeholder = { Text("4532 8912 3456 7890") },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("card_number_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Cardholder Name
        OutlinedTextField(
          value = holderName,
          onValueChange = { holderName = it },
          label = { Text("Cardholder Name") },
          placeholder = { Text("AKRAM KHAN") },
          singleLine = true,
          keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("card_holder_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bank Name
        OutlinedTextField(
          value = bankName,
          onValueChange = { bankName = it },
          label = { Text("Bank Name") },
          placeholder = { Text("HDFC Bank, ICICI, SBI...") },
          singleLine = true,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Expiry & CVV
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedTextField(
            value = expiry,
            onValueChange = { input ->
              val digits = input.filter { it.isDigit() }.take(4)
              expiry = if (digits.length >= 3) "${digits.take(2)}/${digits.drop(2)}" else digits
            },
            label = { Text("Expiry (MM/YY)") },
            placeholder = { Text("08/28") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("card_expiry_input")
          )

          OutlinedTextField(
            value = cvv,
            onValueChange = { if (it.length <= 4 && it.all { char -> char.isDigit() }) cvv = it },
            label = { Text("CVV") },
            placeholder = { Text("•••") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("card_cvv_input")
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Security hint
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = Slate600,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Your card information is stored locally with bank-grade encryption.",
            fontSize = 10.sp,
            color = Slate600
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actions
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
              onAddCard(cardNumber, holderName, expiry, cardType, bankName)
            },
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("submit_add_card_button")
          ) {
            Text("Save Card", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
