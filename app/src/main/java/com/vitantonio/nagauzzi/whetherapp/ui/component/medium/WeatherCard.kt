package com.vitantonio.nagauzzi.whetherapp.ui.component.medium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.whetherapp.ui.component.small.WeatherConditionIcon
import com.vitantonio.nagauzzi.whetherapp.ui.component.small.WeatherDetailItem
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme

/**
 * 天気情報カード
 */
@Composable
fun WeatherCard(weather: Weather, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WeatherConditionIcon(
                condition = weather.condition,
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${weather.temperature}°C",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "最高: ${weather.maxTemperature}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "最低: ${weather.minTemperature}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "最高",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "最低",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weather.condition.toJapanese(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    emoji = "💧",
                    label = "湿度",
                    value = "${weather.humidity}%",
                    modifier = Modifier.weight(1f)
                )

                WeatherDetailItem(
                    emoji = "🌬️",
                    label = "風速",
                    value = "${weather.windSpeed} m/s",
                    modifier = Modifier.weight(1f)
                )

                WeatherDetailItem(
                    emoji = "🌧️",
                    label = "雨量",
                    value = "${weather.rainfall} mm/h",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 天気の状態を日本語に変換する拡張関数
 */
private fun WeatherCondition.toJapanese(): String {
    return when (this) {
        WeatherCondition.SUNNY -> "晴れ"
        WeatherCondition.PARTLY_CLOUDY -> "晴れ時々曇り"
        WeatherCondition.CLOUDY -> "曇り"
        WeatherCondition.RAINY -> "雨"
        WeatherCondition.STORMY -> "嵐"
        WeatherCondition.UNKNOWN -> "不明"
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    WhetherAppTheme {
        WeatherCard(
            weather = Weather(
                city = "東京",
                temperature = 25,
                maxTemperature = 28,
                minTemperature = 21,
                condition = WeatherCondition.SUNNY,
                humidity = 60,
                windSpeed = 3.5,
                rainfall = 0.0
            )
        )
    }
}
