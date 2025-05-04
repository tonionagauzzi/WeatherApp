package com.vitantonio.nagauzzi.weatherapp.repository.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Geocoding APIのサービスインターフェース
 */
interface GeocodingService {
    /**
     * 地名から座標を取得する
     *
     * @param address 住所や都市名
     * @param limit 結果の最大数
     * @return 地理情報リスト
     */
    @GET("geocoding/v1/forward")
    suspend fun geocode(
        @Query("address") address: String,
        @Query("limit") limit: Int = 1,
    ): GeocodingResponse
}

/**
 * Geocoding APIのレスポンスクラス
 */
@JsonClass(generateAdapter = true)
data class GeocodingResponse(
    @Json(name = "results")
    val results: List<GeocodingResult>,
)

/**
 * 座標情報を含む地理情報クラス
 */
@JsonClass(generateAdapter = true)
data class GeocodingResult(
    @Json(name = "name")
    val name: String,
    @Json(name = "geometry")
    val geometry: Geometry,
)

/**
 * 地理座標データクラス
 */
@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lng")
    val longitude: Double,
)
