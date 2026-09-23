package com.nutrino.timbreassignment.presentation.screens.homescreen.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Top App Bar header for the Home Screen displaying app branding.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenHeader() {
    TopAppBar(
        title = { Text(text = "Timbre Audio & Video") }
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenHeaderPreview() {
    TimbreAssignmentTheme {
        HomeScreenHeader()
    }
}
