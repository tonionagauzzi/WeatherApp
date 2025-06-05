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

    /**
     * フェイクの天気データを生成する（APIに接続できない場合のフォールバック）
     */
    private fun getFakeWeather(cityName: String): Weather {
        return when (cityName) {
            "東京" -> Weather(
                city = "東京",
                temperature = 25,
                maxTemperature = 28,
                minTemperature = 21,
                condition = WeatherCondition.SUNNY,
                humidity = 60,
                windSpeed = 3.5,
                rainfall = 0.0,
                dailyForecasts = listOf(
                    DailyForecast("2023-01-01", 28, 21, WeatherCondition.SUNNY),
                    DailyForecast("2023-01-02", 27, 20, WeatherCondition.PARTLY_CLOUDY),
                    DailyForecast("2023-01-03", 26, 19, WeatherCondition.CLOUDY),
                )
            )

            "大阪" -> Weather(
                city = "大阪",
                temperature = 27,
                maxTemperature = 30,
                minTemperature = 23,
                condition = WeatherCondition.CLOUDY,
                humidity = 65,
                windSpeed = 4.0,
                rainfall = 2.0,
                dailyForecasts = listOf(
                    DailyForecast("2023-01-01", 30, 23, WeatherCondition.CLOUDY),
                    DailyForecast("2023-01-02", 29, 22, WeatherCondition.RAINY),
                    DailyForecast("2023-01-03", 28, 21, WeatherCondition.STORMY),
                )
            )

            "札幌" -> Weather(
                city = "札幌",
                temperature = 15,
                maxTemperature = 18,
                minTemperature = 12,
                condition = WeatherCondition.RAINY,
                humidity = 80,
                windSpeed = 5.2,
                rainfall = 5.5,
                dailyForecasts = listOf(
                    DailyForecast("2023-01-01", 18, 12, WeatherCondition.RAINY),
                    DailyForecast("2023-01-02", 17, 11, WeatherCondition.RAINY),
                    DailyForecast("2023-01-03", 16, 10, WeatherCondition.PARTLY_CLOUDY),
                )
            )

            "福岡" -> Weather(
                city = "福岡",
                temperature = 26,
                maxTemperature = 29,
                minTemperature = 22,
                condition = WeatherCondition.CLOUDY,
                humidity = 70,
                windSpeed = 3.0,
                rainfall = 1.5,
                dailyForecasts = listOf(
                    DailyForecast("2023-01-01", 29, 22, WeatherCondition.CLOUDY),
                    DailyForecast("2023-01-02", 28, 21, WeatherCondition.SUNNY),
                    DailyForecast("2023-01-03", 27, 20, WeatherCondition.PARTLY_CLOUDY),
                )
            )

            else -> {
                val temp = (20..30).random()
                val rain = if (Math.random() > 0.5) (0..50).random() / 10.0 else 0.0
                Weather(
                    city = cityName,
                    temperature = temp,
                    maxTemperature = (temp + 3),
                    minTemperature = (temp - 3),
                    condition = WeatherCondition.values().random(),
                    humidity = (50..90).random(),
                    windSpeed = (1..7).random() + (0..9).random() / 10.0,
                    rainfall = rain,
                    dailyForecasts = listOf(
                        DailyForecast("2023-01-01", temp + 3, temp - 3, WeatherCondition.values().random()),
                        DailyForecast("2023-01-02", temp + 2, temp - 4, WeatherCondition.values().random()),
                        DailyForecast("2023-01-03", temp + 1, temp - 5, WeatherCondition.values().random()),
                    )
                )
            }
        }
    }
}
