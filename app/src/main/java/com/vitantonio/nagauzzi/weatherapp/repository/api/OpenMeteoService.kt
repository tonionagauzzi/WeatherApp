package com.vitantonio.nagauzzi.weatherapp.repository.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Open Meteo APIのインターフェース
 */
interface OpenMeteoService {
    /**
     * 指定した緯度・経度の天気情報を取得する
     *
     * @param latitude 緯度
     * @param longitude 経度
     * @param daily 日毎の天気情報の種類（例："temperature_2m_max,temperature_2m_min"）
     * @param current 現在の天気情報の種類（例："temperature_2m,weather_code,wind_speed_10m,precipitation,rain,relative_humidity_2m"）
     * @param timezone タイムゾーン（例："Asia/Tokyo"）
     * @return OpenMeteoResponse オブジェクト
     */
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code",
        @Query("current") current: String = "temperature_2m,weather_code,wind_speed_10m,precipitation,rain,relative_humidity_2m",
        @Query("timezone") timezone: String = "Asia/Tokyo",
    ): OpenMeteoResponse
}

/**
 * Open Meteo APIのレスポンスモデル
 */
data class OpenMeteoResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeather,
    val current_units: CurrentUnits,
    val daily: DailyWeather,
    val daily_units: DailyUnits,
)

/**
 * 現在の天気情報
 */
data class CurrentWeather(
    val time: String,
    val temperature_2m: Double,
    val weather_code: Int,
    val wind_speed_10m: Double,
    val precipitation: Double,
    val rain: Double,
    val relative_humidity_2m: Int,
)

/**
 * 現在の天気情報の単位
 */
data class CurrentUnits(
    val temperature_2m: String,
    val weather_code: String,
    val wind_speed_10m: String,
    val precipitation: String,
    val rain: String,
    val relative_humidity_2m: String,
)

/**
 * 日毎の天気情報
 */
data class DailyWeather(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val weather_code: List<Int>,
)

/**
 * 日毎の天気情報の単位
 */
data class DailyUnits(
    val temperature_2m_max: String,
    val temperature_2m_min: String,
)
