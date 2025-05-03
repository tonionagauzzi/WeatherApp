package com.vitantonio.nagauzzi.whetherapp.repository.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Yahoo!気象情報APIのサービスインターフェース
 */
interface YahooWeatherService {
    /**
     * 座標から天気情報を取得する
     * @param coordinates 経度,緯度の形式で指定した座標
     * @param appid Client ID（アプリケーションID）
     * @param output 出力形式（json）
     * @return 天気情報レスポンス
     */
    @GET("weather/V1/place")
    suspend fun getWeather(
        @Query("coordinates") coordinates: String,
        @Query("appid") appid: String = NetworkModule.YAHOO_APPLICATION_ID,
        @Query("output") output: String = "json",
    ): YahooWeatherResponse
}

/**
 * Yahoo!気象情報APIのレスポンスクラス
 */
@JsonClass(generateAdapter = true)
data class YahooWeatherResponse(
    @Json(name = "ResultInfo")
    val resultInfo: ResultInfo,
    @Json(name = "Feature")
    val features: List<Feature>,
)

@JsonClass(generateAdapter = true)
data class ResultInfo(
    @Json(name = "Count")
    val count: Int,
    @Json(name = "Total")
    val total: Int,
    @Json(name = "Status")
    val status: String,
)

@JsonClass(generateAdapter = true)
data class Feature(
    @Json(name = "Name")
    val name: String,
    @Json(name = "Property")
    val property: Property,
)

@JsonClass(generateAdapter = true)
data class Property(
    @Json(name = "WeatherAreaCode")
    val weatherAreaCode: String,
    @Json(name = "WeatherList")
    val weatherList: WeatherList,
)

@JsonClass(generateAdapter = true)
data class WeatherList(
    @Json(name = "Weather")
    val weather: List<WeatherData>,
)

@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "Type")
    val type: String,
    @Json(name = "Date")
    val date: String,
    @Json(name = "Rainfall")
    val rainfall: Double,
)
