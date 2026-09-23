package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Text field component for specifying the trimmed output file name.
 *
 * @param fileName Current file name string value.
 * @param onFileNameChange Text modification callback.
 * @param modifier Layout modifier.
 */
@Composable
fun OutputFileNameInput(
    fileName: String,
    onFileNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = fileName,
        onValueChange = onFileNameChange,
        label = { Text("Output Audio Name") },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Preview(showBackground = true)
@Composable
private fun OutputFileNameInputPreview() {
    TimbreAssignmentTheme {
        OutputFileNameInput(
            fileName = "Trimmed_Song_1",
            onFileNameChange = {}
        )
    }
}
