package com.vitantonio.nagauzzi.whetherapp.ui.component.small

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RetryButton(onRetry: () -> Unit) {
    Button(
        onClick = onRetry,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = "再取得")
    }
}
