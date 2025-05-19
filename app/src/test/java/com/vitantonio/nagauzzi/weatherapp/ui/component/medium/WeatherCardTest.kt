package com.vitantonio.nagauzzi.weatherapp.ui.component.medium

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.vitantonio.nagauzzi.weatherapp.R
import com.vitantonio.nagauzzi.weatherapp.model.Weather
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.CLOUDY
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.PARTLY_CLOUDY
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.RAINY
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.SNOWY
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.STORMY
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition.SUNNY
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

@Config(qualifiers = "ja")
@RunWith(ParameterizedRobolectricTestRunner::class)
class WeatherCardTest(
    @Suppress("unused")
    private val testCase: String,
    private val condition: WeatherCondition,
    private val labelResourceId: Int,
    private val emoji: String,
) {
    companion object {
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        @JvmStatic
        fun data(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf("晴れ", SUNNY, R.string.label_weather_sunny, "☀️"),
                arrayOf("晴れ時々曇り", PARTLY_CLOUDY, R.string.label_weather_partly_cloudy, "🌤️"),
                arrayOf("曇り", CLOUDY, R.string.label_weather_cloudy, "☁️"),
                arrayOf("雨", RAINY, R.string.label_weather_rainy, "☔️"),
                arrayOf("雪", SNOWY, R.string.label_weather_snowy, "❄️"),
                arrayOf("嵐", STORMY, R.string.label_weather_stormy, "⚡️")
            )
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun `天気情報の領域には、加工された天気データが正しく表示される`() {
        // テスト用の天気データ
        val weather = Weather(
            city = "東京",
            temperature = 2,
            maxTemperature = 8,
            minTemperature = -1,
            condition = condition,
            humidity = 90,
            windSpeed = 3.5,
            rainfall = 1.0
        )

        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherCard(weather = weather)
            }
        }

        // 天気情報が表示されることを確認
        composeTestRule.onNodeWithText("東京").assertIsDisplayed()
        composeTestRule.onNodeWithText(emoji).assertIsDisplayed()
        composeTestRule.onNodeWithText("2°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最高: 8°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最低: -1°C").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(labelResourceId)).assertIsDisplayed()
        composeTestRule.onNodeWithText("湿度").assertIsDisplayed()
        composeTestRule.onNodeWithText("90%").assertIsDisplayed()
        composeTestRule.onNodeWithText("風速").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.5 m/s").assertIsDisplayed()
        composeTestRule.onNodeWithText("雨量").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.0 mm/h").assertIsDisplayed()
    }
}
