package com.vitantonio.nagauzzi.whetherapp.repository

import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * テスト用のモックWeatherRepository
 */
class MockWeatherRepository : WeatherRepository {
    // テスト結果をコントロールするためのフラグ
    var shouldReturnError = false

    /**
     * 指定した都市の天気情報を取得する
     * テスト用に固定データを返す
     *
     * @param cityName 都市名
     * @return 天気情報のFlow
     */
    override fun getWeatherForCity(cityName: String): Flow<Weather> = flow {
        if (shouldReturnError) {
            throw Exception("テスト用エラー")
        }

        // テスト用の固定データを返す
        val mockWeather = when (cityName) {
            "東京" -> Weather(
                city = "東京",
                temperature = 25,
                maxTemperature = 28,
                minTemperature = 21,
                condition = WeatherCondition.SUNNY,
                humidity = 60,
                windSpeed = 3.5
            )
            "大阪" -> Weather(
                city = "大阪",
                temperature = 27,
                maxTemperature = 30,
                minTemperature = 23,
                condition = WeatherCondition.CLOUDY,
                humidity = 65,
                windSpeed = 4.0
            )
            else -> Weather(
                city = cityName,
                temperature = 20,
                maxTemperature = 25,
                minTemperature = 15,
                condition = WeatherCondition.PARTLY_CLOUDY,
                humidity = 70,
                windSpeed = 2.5
            )
        }

        emit(mockWeather)
    }

    /**
     * デフォルトの都市リストを取得する
     * テスト用に固定リストを返す
     */
    override fun getDefaultCities(): List<String> {
        return listOf("東京", "大阪", "テスト都市")
    }
}
