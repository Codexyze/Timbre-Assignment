package com.nutrino.timbreassignment.presentation.screens.homescreen.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.timbreassignment.presentation.screens.homescreen.model.HomeTab
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Bottom navigation bar allowing users to switch between home screen tabs.
 *
 * @param selectedTab The currently active [HomeTab].
 * @param onTabSelected Callback function invoked when a navigation tab is tapped.
 */
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
