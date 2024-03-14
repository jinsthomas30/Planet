package com.example.planet.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.planet.state.DialogState

@Composable
fun DialogView(
    dialogState: DialogState,
    onDismiss: () -> Unit
) {
    if (dialogState is DialogState.Show) {
        AlertDialog(
            onDismissRequest = { onDismiss()},
            title = {
                Text(dialogState.message, fontSize = 16.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(dialogState.buttonText, fontSize = 16.sp)
                }
            }
        )
    }
}