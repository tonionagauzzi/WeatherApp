package com.vitantonio.nagauzzi.weatherapp.repository

import com.vitantonio.nagauzzi.weatherapp.model.DailyForecast
import com.vitantonio.nagauzzi.weatherapp.model.Location
import com.vitantonio.nagauzzi.weatherapp.model.Weather
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.weatherapp.repository.api.NetworkModule
import kotlin.math.roundToInt

/**
 * 天気情報を提供するリポジトリ
 */
interface WeatherRepository {
    /**
     * 指定した都市の天気情報を取得する
     *
     * @param cityName 都市名
     * @return 天気情報のFlow
     */
    suspend fun getWeatherForCity(cityName: String): Result<Weather>

    /**
     * デフォルトの都市リストを取得する
     */
    fun getDefaultCities(): List<String>
}

class WeatherRepositoryImpl : WeatherRepository {
    private val geocodingService = NetworkModule.geocodingService
    private val openMeteoService = NetworkModule.openMeteoService

    override suspend fun getWeatherForCity(cityName: String): Result<Weather> = runCatching {
        // 都市名から座標を取得
        val location = getLocationFromCityName(cityName)

        // 座標から天気情報を取得（Open Meteo API）
        val openMeteoResponse = openMeteoService.getWeather(
            latitude = location.latitude,
            longitude = location.longitude
        )

        // 現在の天気データを取得
        val currentWeather = openMeteoResponse.current
        val dailyWeather = openMeteoResponse.daily

        // 天気コードから天気の状態を判定
        val condition = WeatherCondition.fromOpenMeteoWeatherCode(currentWeather.weather_code)

        // 日毎の天気情報を作成
        val dailyForecasts = dailyWeather.time.mapIndexed { index, date ->
            DailyForecast(
                date = date,
                maxTemperature = dailyWeather.temperature_2m_max[index].roundToInt(),
                minTemperature = dailyWeather.temperature_2m_min[index].roundToInt(),
                condition = WeatherCondition.fromOpenMeteoWeatherCode(dailyWeather.weather_code[index])
            )
        }

        // Weather オブジェクトを作成
        Weather(
            city = cityName,
            temperature = currentWeather.temperature_2m.roundToInt(),
            maxTemperature = dailyWeather.temperature_2m_max.first().roundToInt(),
            minTemperature = dailyWeather.temperature_2m_min.first().roundToInt(),
            condition = condition,
            humidity = currentWeather.relative_humidity_2m,
            windSpeed = currentWeather.wind_speed_10m,
            rainfall = currentWeather.rain,
            dailyForecasts = dailyForecasts
        )
    }

    override fun getDefaultCities(): List<String> {
        return listOf("東京", "大阪", "札幌", "福岡", "名古屋")
    }

    /**
     * 都市名から位置情報を取得する
     */
    private suspend fun getLocationFromCityName(cityName: String): Location = runCatching {
        val response = geocodingService.geocode(cityName)
        val result = response.results.first()
        return Location(latitude = result.geometry.latitude, longitude = result.geometry.longitude)
    }.fold(
        onSuccess = { location ->
            // 位置情報を取得できた場合はそのまま返す
            location
        },
        onFailure = {
            // 位置情報を取得できない場合はデフォルトの位置情報を返す
            getDefaultLocation(cityName)
        }
    )

    /**
     * 都市名に基づいたデフォルトの位置情報を返す
     */
    private fun getDefaultLocation(cityName: String): Location {
        return when (cityName) {
            "東京" -> Location(35.6762, 139.6503)
            "大阪" -> Location(34.6937, 135.5023)
            "札幌" -> Location(43.0621, 141.3544)
            "福岡" -> Location(33.5904, 130.4017)
            "名古屋" -> Location(35.1815, 136.9066)
            else -> Location(35.6762, 139.6503) // デフォルトは東京
        }
    }
}
