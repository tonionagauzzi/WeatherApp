package com.vitantonio.nagauzzi.weatherapp.model

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
         * Open MeteoのWMO Weather Codeから天気の状態を判定する
         * https://open-meteo.com/en/docs 参照
         * @param weatherCode WMO Weather Code
         * @return 天気の状態
         */
        fun fromOpenMeteoWeatherCode(weatherCode: Int): WeatherCondition {
            return when (weatherCode) {
                0 -> SUNNY // Clear sky
                1 -> SUNNY // Mainly clear
                2 -> PARTLY_CLOUDY // Partly cloudy
                3 -> CLOUDY // Overcast
                45, 48 -> CLOUDY // Fog and depositing rime fog
                51, 53, 55 -> RAINY // Drizzle: Light, moderate, and dense intensity
                56, 57 -> RAINY // Freezing Drizzle: Light and dense intensity
                61, 63, 65 -> RAINY // Rain: Slight, moderate and heavy intensity
                66, 67 -> RAINY // Freezing Rain: Light and heavy intensity
                71, 73, 75 -> RAINY // Snow fall: Slight, moderate, and heavy intensity
                77 -> RAINY // Snow grains
                80, 81, 82 -> RAINY // Rain showers: Slight, moderate, and violent
                85, 86 -> RAINY // Snow showers slight and heavy
                95 -> STORMY // Thunderstorm: Slight or moderate
                96, 99 -> STORMY // Thunderstorm with slight and heavy hail
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
