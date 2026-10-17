package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate400
import com.example.viewmodel.AppTab

@Composable
fun AkramBottomBar(
  currentTab: AppTab,
  onTabSelected: (AppTab) -> Unit,
  activeOrdersCount: Int,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    // Home Tab
    NavigationBarItem(
      selected = currentTab == AppTab.HOME,
      onClick = { onTabSelected(AppTab.HOME) },
      icon = {
        Icon(
          imageVector = if (currentTab == AppTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
          contentDescription = "Home"
        )
      },
      label = {
        Text(
          text = "Home",
          fontWeight = if (currentTab == AppTab.HOME) FontWeight.Bold else FontWeight.Normal,
          fontSize = 12.sp
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BrandBlue,
        selectedTextColor = BrandBlue,
        unselectedIconColor = Slate400,
        unselectedTextColor = Slate400,
        indicatorColor = BrandBlue.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("bottom_nav_home")
    )

    // Account Tab
    NavigationBarItem(
      selected = currentTab == AppTab.ACCOUNT,
      onClick = { onTabSelected(AppTab.ACCOUNT) },
      icon = {
        BadgedBox(
          badge = {
            if (activeOrdersCount > 0) {
              Badge(
                containerColor = BrandAmber,
                contentColor = Color.White
              ) {
                Text(
                  text = "$activeOrdersCount",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        ) {
          Icon(
            imageVector = if (currentTab == AppTab.ACCOUNT) Icons.Filled.Person else Icons.Outlined.Person,
            contentDescription = "Account"
          )
        }
      },
      label = {
        Text(
          text = "Account",
          fontWeight = if (currentTab == AppTab.ACCOUNT) FontWeight.Bold else FontWeight.Normal,
          fontSize = 12.sp
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BrandBlue,
        selectedTextColor = BrandBlue,
        unselectedIconColor = Slate400,
        unselectedTextColor = Slate400,
        indicatorColor = BrandBlue.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("bottom_nav_account")
    )
  }
}
