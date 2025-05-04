package com.vitantonio.nagauzzi.weatherapp.ui.component.small

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition

/**
 * 天気の状態アイコン
 */
@Composable
fun WeatherConditionIcon(condition: WeatherCondition, modifier: Modifier = Modifier) {
    val emoji = when (condition) {
        WeatherCondition.SUNNY -> "☀️"
        WeatherCondition.PARTLY_CLOUDY -> "🌤️"
        WeatherCondition.CLOUDY -> "☁️"
        WeatherCondition.RAINY -> "🌧️"
        WeatherCondition.STORMY -> "⚡"
        WeatherCondition.UNKNOWN -> "？"
    }

    Text(
        text = emoji,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
