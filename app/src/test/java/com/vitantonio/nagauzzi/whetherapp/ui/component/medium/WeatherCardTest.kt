package com.vitantonio.nagauzzi.whetherapp.ui.component.medium

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `天気情報の領域には、加工された天気データが正しく表示される`() {
        // テスト用の天気データ
        val weather = Weather(
            city = "東京",
            temperature = 25,
            maxTemperature = 28,
            minTemperature = 21,
            condition = WeatherCondition.SUNNY,
            humidity = 60,
            windSpeed = 3.5,
            rainfall = 0.0
        )

        // コンポーネントを設定
        composeTestRule.setContent {
            WhetherAppTheme {
                WeatherCard(weather = weather)
            }
        }

        // 天気情報が表示されることを確認
        composeTestRule.onNodeWithText("東京").assertIsDisplayed()
        composeTestRule.onNodeWithText("25°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最高: 28°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最低: 21°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("晴れ").assertIsDisplayed()
        composeTestRule.onNodeWithText("湿度").assertIsDisplayed()
        composeTestRule.onNodeWithText("60%").assertIsDisplayed()
        composeTestRule.onNodeWithText("風速").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.5 m/s").assertIsDisplayed()
        composeTestRule.onNodeWithText("雨量").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.0 mm/h").assertIsDisplayed()
    }
}
