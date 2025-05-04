package com.vitantonio.nagauzzi.weatherapp.ui.component.large

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.weatherapp.model.Weather
import com.vitantonio.nagauzzi.weatherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import com.vitantonio.nagauzzi.weatherapp.viewmodel.WeatherUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `天気情報の取得中は、インジケーターが表示される`() {
        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherScreen(
                    weatherState = WeatherUiState.Loading,
                    selectedCity = "東京",
                    availableCities = listOf("東京", "大阪", "札幌"),
                    onCitySelected = { },
                    onRetryFetch = { },
                    modifier = Modifier.Companion
                )
            }
        }

        // インジケーターが表示されることを確認
        composeTestRule.onNodeWithTag("progress_indicator").assertIsDisplayed()

        // 天気情報が表示されないことを確認
        composeTestRule.onNodeWithText("湿度").assertIsNotDisplayed()

        // エラーメッセージが表示されないことを確認
        composeTestRule.onNodeWithText("エラーが発生しました").assertIsNotDisplayed()

        // 再取得ボタンが表示されないことを確認
        composeTestRule.onNodeWithText("再取得").assertIsNotDisplayed()
    }

    @Test
    fun `天気情報の取得に成功したら、取得した情報が表示される`() {
        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherScreen(
                    weatherState = WeatherUiState.Success(
                        weather = Weather(
                            city = "東京",
                            temperature = 25,
                            maxTemperature = 28,
                            minTemperature = 21,
                            condition = WeatherCondition.SUNNY,
                            humidity = 60,
                            windSpeed = 3.5,
                            rainfall = 0.0
                        )
                    ),
                    selectedCity = "東京",
                    availableCities = listOf("東京", "大阪", "札幌"),
                    onCitySelected = { },
                    onRetryFetch = { },
                    modifier = Modifier.Companion
                )
            }
        }

        // インジケーターが表示されないことを確認
        composeTestRule.onNodeWithTag("progress_indicator").assertIsNotDisplayed()

        // 天気情報が表示されることを確認
        composeTestRule.onNodeWithText("25°C").assertIsDisplayed()

        // エラーメッセージが表示されないことを確認
        composeTestRule.onNodeWithText("エラーが発生しました").assertIsNotDisplayed()

        // 再取得ボタンまでスクロールする
        composeTestRule.onNodeWithText("再取得").performScrollTo()

        // 再取得ボタンが表示されることを確認
        composeTestRule.onNodeWithText("再取得").assertIsDisplayed()
    }

    @Test
    fun `天気情報の取得に失敗したら、エラーメッセージが表示される`() {
        var retryFetchCalled = false

        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherScreen(
                    weatherState = WeatherUiState.Error("ネットワークエラーが発生しました"),
                    selectedCity = "東京",
                    availableCities = listOf("東京", "大阪", "札幌"),
                    onCitySelected = { },
                    onRetryFetch = { retryFetchCalled = true },
                    modifier = Modifier.Companion
                )
            }
        }

        // インジケーターが表示されないことを確認
        composeTestRule.onNodeWithTag("progress_indicator").assertIsNotDisplayed()

        // 天気情報が表示されないことを確認
        composeTestRule.onNodeWithText("湿度").assertIsNotDisplayed()

        // エラーメッセージが表示されることを確認
        composeTestRule.onNodeWithText("エラーが発生しました").assertIsDisplayed()

        // 再取得ボタンが表示されることを確認
        composeTestRule.onNodeWithText("再取得").assertIsDisplayed()

        // 再取得ボタンをクリック
        composeTestRule.onNodeWithText("再取得").performClick()
        assert(retryFetchCalled) { "再取得ボタンが呼び出されていません" }
    }
}
