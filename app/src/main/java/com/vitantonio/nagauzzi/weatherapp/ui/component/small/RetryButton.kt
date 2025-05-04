package com.vitantonio.nagauzzi.weatherapp.ui.component.small

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vitantonio.nagauzzi.weatherapp.R

@Composable
fun RetryButton(onRetry: () -> Unit) {
    Button(
        onClick = onRetry,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = LocalContext.current.getString(R.string.label_retry))
    }
}
