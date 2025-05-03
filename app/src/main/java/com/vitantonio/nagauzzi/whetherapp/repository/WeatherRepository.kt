package com.vitantonio.nagauzzi.whetherapp.repository

import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 天気情報を提供するリポジトリ
 */
class WeatherRepository {
    /**
     * 指定した都市の天気情報を取得する
     *
     * @param cityName 都市名
     * @return 天気情報のFlow
     */
    fun getWeatherForCity(cityName: String): Flow<Weather> = flow {
        // フェイクの天気データを生成
        val fakeWeather = when (cityName) {
            "東京" -> Weather(
                city = "東京",
                maxTemperature = 28,
                minTemperature = 21,
                condition = WeatherCondition.SUNNY,
                humidity = 60,
                windSpeed = 3.5
            )
            "大阪" -> Weather(
                city = "大阪",
                maxTemperature = 30,
                minTemperature = 23,
                condition = WeatherCondition.CLOUDY,
                humidity = 65,
                windSpeed = 4.0
            )
            "札幌" -> Weather(
                city = "札幌",
                maxTemperature = 18,
                minTemperature = 12,
                condition = WeatherCondition.RAINY,
                humidity = 80,
                windSpeed = 5.2
            )
            "福岡" -> Weather(
                city = "福岡",
                maxTemperature = 29,
                minTemperature = 22,
                condition = WeatherCondition.CLOUDY,
                humidity = 70,
                windSpeed = 3.0
            )
            else -> {
                val maxTemp = (20..32).random()
                val minTemp = (maxTemp - 10).coerceAtLeast(10)
                Weather(
                    city = cityName,
                    maxTemperature = maxTemp,
                    minTemperature = minTemp,
                    condition = WeatherCondition.values().random(),
                    humidity = (50..90).random(),
                    windSpeed = (1..7).random() + (0..9).random() / 10.0
                )
            }
        }
        emit(fakeWeather)
    }

    /**
     * デフォルトの都市リストを取得する
     */
    fun getDefaultCities(): List<String> {
        return listOf("東京", "大阪", "札幌", "福岡", "名古屋")
    }
}
