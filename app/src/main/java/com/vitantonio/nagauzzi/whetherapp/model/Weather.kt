package com.vitantonio.nagauzzi.whetherapp.model

/**
 * 天気情報のデータモデル
 */
data class Weather(
    val city: String,
    val temperature: Int,
    val maxTemperature: Int,
    val minTemperature: Int,
    val condition: WeatherCondition,
    val humidity: Int,
    val windSpeed: Double,
    val rainfall: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis(),
)

/**
 * 天気の状態を表す列挙型
 */
enum class WeatherCondition {
    SUNNY,
    PARTLY_CLOUDY,
    CLOUDY,
    RAINY,
    STORMY,
    UNKNOWN,
    ;

    companion object {
        /**
         * Yahoo!気象情報APIの降水強度から天気の状態を判定する
         * @param rainfall 降水強度（mm/h）
         * @return 天気の状態
         */
        fun fromYahooRainfall(rainfall: Double): WeatherCondition {
            return when {
                rainfall == 0.0 -> SUNNY
                rainfall < 1.0 -> PARTLY_CLOUDY
                rainfall < 3.0 -> CLOUDY
                rainfall < 20.0 -> RAINY
                rainfall >= 20.0 -> STORMY
                else -> UNKNOWN
            }
        }
    }
}

/**
 * 位置情報を表すデータクラス
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
)
