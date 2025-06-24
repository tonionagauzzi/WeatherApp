package com.vitantonio.nagauzzi.weatherapp.ui.component.medium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.weatherapp.model.DailyForecast
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.WeatherConditionIcon

@Composable
fun DailyForecastRow(forecast: DailyForecast, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forecast.date,
            style = MaterialTheme.typography.bodyMedium
        )
        WeatherConditionIcon(condition = forecast.condition)
        Text(
            text = "${forecast.maxTemperature}°C / ${forecast.minTemperature}°C",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DailyForecastRowPreview() {
    val forecasts = listOf(
        DailyForecast(
            date = "今日",
            maxTemperature = 25,
            minTemperature = 18,
            condition = WeatherCondition.SUNNY
        ),
        DailyForecast(
            date = "明日",
            maxTemperature = 22,
            minTemperature = 15,
            condition = WeatherCondition.CLOUDY
        ),
        DailyForecast(
            date = "明後日",
            maxTemperature = 19,
            minTemperature = 12,
            condition = WeatherCondition.RAINY
        )
    )

    MaterialTheme {
        Column {
            forecasts.forEach { forecast ->
                DailyForecastRow(forecast = forecast)
            }
        }
    }
}
