package com.vitantonio.nagauzzi.weatherapp.fake

import com.vitantonio.nagauzzi.weatherapp.model.DailyForecast
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition

val fakeDailyForecasts: List<DailyForecast>
    get() = listOf(
        DailyForecast(
            date = "2023-10-01",
            condition = WeatherCondition.SUNNY,
            maxTemperature = 28,
            minTemperature = 21
        ),
        DailyForecast(
            date = "2023-10-02",
            condition = WeatherCondition.PARTLY_CLOUDY,
            maxTemperature = 26,
            minTemperature = 20
        ),
        DailyForecast(
            date = "2023-10-03",
            condition = WeatherCondition.CLOUDY,
            maxTemperature = 24,
            minTemperature = 19
        )
    )
