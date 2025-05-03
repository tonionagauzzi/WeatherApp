package com.vitantonio.nagauzzi.whetherapp.model

/**
 * 天気情報のデータモデル
 */
data class Weather(
    val city: String,
    val maxTemperature: Int,
    val minTemperature: Int,
    val condition: WeatherCondition,
    val humidity: Int,
    val windSpeed: Double,
)

/**
 * 天気の状態を表す列挙型
 */
enum class WeatherCondition {
    SUNNY,
    CLOUDY,
    RAINY,
    SNOWY,
    STORMY,
}
