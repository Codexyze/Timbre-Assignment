package com.nutrino.timbreassignment.presentation.screens.homescreen.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.timbreassignment.presentation.screens.homescreen.model.HomeTab
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@Composable
fun HomeScreenBottomBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    NavigationBar {
        HomeTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(text = tab.title)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenBottomBarPreview() {
    TimbreAssignmentTheme {
        HomeScreenBottomBar(
            selectedTab = HomeTab.AUDIO,
            onTabSelected = {}
        )
    }
}
