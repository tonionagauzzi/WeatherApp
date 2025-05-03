package com.vitantonio.nagauzzi.whetherapp.repository

import com.vitantonio.nagauzzi.whetherapp.model.Location
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.whetherapp.repository.api.NetworkModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
    fun getWeatherForCity(cityName: String): Flow<Weather>

    /**
     * デフォルトの都市リストを取得する
     */
    fun getDefaultCities(): List<String>
}

class WeatherRepositoryImpl : WeatherRepository {
    private val geocodingService = NetworkModule.geocodingService
    private val yahooWeatherService = NetworkModule.yahooWeatherService

    override fun getWeatherForCity(cityName: String): Flow<Weather> = flow {
        try {
            // 都市名から座標を取得
            val location = getLocationFromCityName(cityName)

            // 座標から天気情報を取得
            // Yahoo!気象情報APIに必要な形式（経度,緯度）に変換
            val coordinates = "${location.longitude},${location.latitude}"
            val yahooResponse = yahooWeatherService.getWeather(coordinates = coordinates)

            // レスポンスのチェック
            if (yahooResponse.resultInfo.status != "200" || yahooResponse.features.isEmpty()) {
                throw Exception("天気情報の取得に失敗しました")
            }

            // 現在の天気データを取得
            val feature = yahooResponse.features[0]
            val currentWeather = feature.property.weatherList.weather.firstOrNull { it.type == "observation" }
                ?: throw Exception("現在の天気データが取得できませんでした")

            // 降水強度から天気の状態を判定
            val rainfall = currentWeather.rainfall
            val condition = WeatherCondition.fromYahooRainfall(rainfall)

            // 雨量から簡易的に温度と湿度を推定（実際のAPIでは温度・湿度データは提供されていないため）
            // 実際のアプリでは、別のAPIから温度や湿度を取得することをお勧めします
            val temperature = estimateTemperature(cityName, rainfall)
            val humidity = estimateHumidity(rainfall)
            val windSpeed = estimateWindSpeed(rainfall)

            // Weather オブジェクトを作成
            val weather = Weather(
                city = cityName,
                temperature = temperature,
                maxTemperature = (temperature * 1.1).toInt(),
                minTemperature = (temperature * 0.9).toInt(),
                condition = condition,
                humidity = humidity,
                windSpeed = windSpeed,
                rainfall = rainfall
            )

            emit(weather)
        } catch (e: Exception) {
            // エラー発生時はフェイクデータを返す
            emit(getFakeWeather(cityName))
        }
    }

    override fun getDefaultCities(): List<String> {
        return listOf("東京", "大阪", "札幌", "福岡", "名古屋")
    }

    /**
     * 降水強度から温度を推定する（簡易的な実装）
     */
    private fun estimateTemperature(cityName: String, rainfall: Double): Int {
        // 雨量に基づいて温度を簡易的に推定
        val baseTemp = when (cityName) {
            "東京" -> 25
            "大阪" -> 27
            "札幌" -> 15
            "福岡" -> 26
            "名古屋" -> 24
            else -> 22
        }

        // 雨が強いほど気温は下がる傾向と仮定
        return when {
            rainfall <= 0.0 -> baseTemp + 2
            rainfall < 1.0 -> baseTemp
            rainfall < 3.0 -> baseTemp - 1
            rainfall < 10.0 -> baseTemp - 2
            else -> baseTemp - 3
        }
    }

    /**
     * 降水強度から湿度を推定する（簡易的な実装）
     */
    private fun estimateHumidity(rainfall: Double): Int {
        // 雨量に基づいて湿度を簡易的に推定
        return when {
            rainfall <= 0.0 -> 50
            rainfall < 1.0 -> 60
            rainfall < 3.0 -> 70
            rainfall < 10.0 -> 80
            else -> 90
        }.coerceIn(0, 100) // 0〜100の範囲に制限
    }

    /**
     * 降水強度から風速を推定する（簡易的な実装）
     */
    private fun estimateWindSpeed(rainfall: Double): Double {
        // 雨量に基づいて風速を簡易的に推定
        return when {
            rainfall <= 0.0 -> 2.0
            rainfall < 1.0 -> 3.0
            rainfall < 3.0 -> 4.0
            rainfall < 10.0 -> 5.0
            else -> 7.0
        }
    }

    /**
     * 都市名から位置情報を取得する
     */
    private suspend fun getLocationFromCityName(cityName: String): Location {
        try {
            val response = geocodingService.geocode(cityName)
            val result = response.results.firstOrNull()
                ?: throw Exception("位置情報が見つかりませんでした: $cityName")

            return Location(
                latitude = result.geometry.latitude,
                longitude = result.geometry.longitude
            )
        } catch (e: Exception) {
            // 位置情報を取得できない場合のデフォルト値
            return getDefaultLocation(cityName)
        }
    }

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
                rainfall = 0.0
            )
            "大阪" -> Weather(
                city = "大阪",
                temperature = 27,
                maxTemperature = 30,
                minTemperature = 23,
                condition = WeatherCondition.CLOUDY,
                humidity = 65,
                windSpeed = 4.0,
                rainfall = 2.0
            )
            "札幌" -> Weather(
                city = "札幌",
                temperature = 15,
                maxTemperature = 18,
                minTemperature = 12,
                condition = WeatherCondition.RAINY,
                humidity = 80,
                windSpeed = 5.2,
                rainfall = 5.5
            )
            "福岡" -> Weather(
                city = "福岡",
                temperature = 26,
                maxTemperature = 29,
                minTemperature = 22,
                condition = WeatherCondition.CLOUDY,
                humidity = 70,
                windSpeed = 3.0,
                rainfall = 1.5
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
                    rainfall = rain
                )
            }
        }
    }
}
