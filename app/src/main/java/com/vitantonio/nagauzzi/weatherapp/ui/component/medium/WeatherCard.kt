package com.vitantonio.nagauzzi.weatherapp.ui.component.medium

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.weatherapp.R
import com.vitantonio.nagauzzi.weatherapp.model.Weather
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.WeatherConditionIcon
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.WeatherDetailItem
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme

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

            val context = LocalContext.current

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${context.getString(R.string.label_max_temp_prefix)}${weather.maxTemperature}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${context.getString(R.string.label_min_temp_prefix)}${weather.minTemperature}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = weather.condition.toLocalizedString(context),
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
                    label = context.getString(R.string.label_humidity),
                    value = "${weather.humidity}%",
                    modifier = Modifier.weight(1f)
                )

                WeatherDetailItem(
                    emoji = "🌬️",
                    label = context.getString(R.string.label_wind_speed),
                    value = "${weather.windSpeed} m/s",
                    modifier = Modifier.weight(1f)
                )

                WeatherDetailItem(
                    emoji = "🌧️",
                    label = context.getString(R.string.label_rainfall),
                    value = "${weather.rainfall} mm/h",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 天気の状態をローカライズされた文字列に変換する拡張関数
 */
private fun WeatherCondition.toLocalizedString(context: android.content.Context): String {
    return when (this) {
        WeatherCondition.SUNNY -> context.getString(R.string.label_weather_sunny)
        WeatherCondition.PARTLY_CLOUDY -> context.getString(R.string.label_weather_partly_cloudy)
        WeatherCondition.CLOUDY -> context.getString(R.string.label_weather_cloudy)
        WeatherCondition.RAINY -> context.getString(R.string.label_weather_rainy)
        WeatherCondition.STORMY -> context.getString(R.string.label_weather_stormy)
        WeatherCondition.SNOWY -> context.getString(R.string.label_weather_snowy)
        WeatherCondition.UNKNOWN -> context.getString(R.string.label_weather_unknown)
    }
}

@Preview(locale = "ja", showBackground = true)
@Composable
fun WeatherCardPreview() {
    WeatherAppTheme {
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

@Preview(locale = "ja", showBackground = true)
@Composable
fun SnowyWeatherCardPreview() {
    WeatherAppTheme {
        WeatherCard(
            weather = Weather(
                city = "東京",
                temperature = 2,
                maxTemperature = 8,
                minTemperature = -1,
                condition = WeatherCondition.SNOWY,
                humidity = 90,
                windSpeed = 3.5,
                rainfall = 1.0
            )
        )
    }
}
