package com.nutrino.timbreassignment.presentation.screens.videotrimmer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@Composable
fun VideoOutputFileNameInput(
    fileName: String,
    onFileNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = fileName,
        onValueChange = onFileNameChange,
        label = { Text("Output Video Name") },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Preview(showBackground = true)
@Composable
private fun VideoOutputFileNameInputPreview() {
    TimbreAssignmentTheme {
        VideoOutputFileNameInput(
            fileName = "Trimmed_Video_1",
            onFileNameChange = {}
        )
    }
}
