package com.vitantonio.nagauzzi.weatherapp.ui.component.small

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vitantonio.nagauzzi.weatherapp.R
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition

/**
 * 天気の状態アイコン
 */
@Composable
fun WeatherConditionIcon(condition: WeatherCondition, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val emoji = when (condition) {
        WeatherCondition.SUNNY -> "☀️"
        WeatherCondition.PARTLY_CLOUDY -> "🌤️"
        WeatherCondition.CLOUDY -> "☁️"
        WeatherCondition.RAINY -> "☔️"
        WeatherCondition.STORMY -> "⚡️"
        else -> context.getString(R.string.label_question_mark) // FIXME: Use UNKNOWN condition instead of else statement
    }

    Text(
        text = emoji,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
