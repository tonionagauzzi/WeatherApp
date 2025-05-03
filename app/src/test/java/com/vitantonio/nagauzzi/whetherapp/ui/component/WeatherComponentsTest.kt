package com.vitantonio.nagauzzi.whetherapp.ui.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme
import com.vitantonio.nagauzzi.whetherapp.viewmodel.WeatherUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherComponentsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `天気情報の取得中は、ローディング表示となる`() {
        // テスト用のパラメータを設定
        val weatherState = WeatherUiState.Loading
        val selectedCity = "東京"
        val availableCities = listOf("東京", "大阪", "札幌")
        var citySelected = ""
        var retryFetchCalled = false

        // コンポーネントを設定
        composeTestRule.setContent {
            WhetherAppTheme {
                WeatherScreen(
                    weatherState = weatherState,
                    selectedCity = selectedCity,
                    availableCities = availableCities,
                    onCitySelected = { citySelected = it },
                    onRetryFetch = { retryFetchCalled = true },
                    modifier = Modifier
                )
            }
        }

        // ローディング表示が表示されることを確認
        composeTestRule.onNodeWithText("天気情報").assertIsDisplayed()
        composeTestRule.onNodeWithText("東京").assertIsDisplayed()
        composeTestRule.onNodeWithTag("progress_indicator").assertIsDisplayed()

        // ローディング中は再取得ボタンが表示されないことを確認
        composeTestRule.onNodeWithText("再取得").assertDoesNotExist()
    }

    @Test
    fun `天気情報の取得に成功したら、取得した情報が表示される`() {
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
        val weatherState = WeatherUiState.Success(weather)
        val selectedCity = "東京"
        val availableCities = listOf("東京", "大阪", "札幌")
        var citySelected = ""
        var retryFetchCalled = false

        // コンポーネントを設定
        composeTestRule.setContent {
            WhetherAppTheme {
                WeatherScreen(
                    weatherState = weatherState,
                    selectedCity = selectedCity,
                    availableCities = availableCities,
                    onCitySelected = { citySelected = it },
                    onRetryFetch = { retryFetchCalled = true },
                    modifier = Modifier
                )
            }
        }

        // 天気情報が表示されることを確認
        composeTestRule.onNodeWithText("天気情報").assertIsDisplayed()
        composeTestRule.onNodeWithText("25°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最高: 28°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("最低: 21°C").assertIsDisplayed()

        // 再取得ボタンが表示されることを確認
        composeTestRule.onNodeWithTag("再取得").assertIsDisplayed()

        // 再取得ボタンをクリック
        composeTestRule.onNodeWithText("再取得").performClick()
        assert(retryFetchCalled) { "再取得ボタンが呼び出されていません" }
    }

    @Test
    fun `天気情報の取得に失敗したら、エラーメッセージが表示される`() {
        // エラー状態を設定
        val weatherState = WeatherUiState.Error("ネットワークエラーが発生しました")
        val selectedCity = "東京"
        val availableCities = listOf("東京", "大阪", "札幌")
        var citySelected = ""
        var retryFetchCalled = false

        // コンポーネントを設定
        composeTestRule.setContent {
            WhetherAppTheme {
                WeatherScreen(
                    weatherState = weatherState,
                    selectedCity = selectedCity,
                    availableCities = availableCities,
                    onCitySelected = { citySelected = it },
                    onRetryFetch = { retryFetchCalled = true },
                    modifier = Modifier
                )
            }
        }

        // エラーメッセージが表示されることを確認
        composeTestRule.onNodeWithText("天気情報").assertIsDisplayed()
        composeTestRule.onNodeWithText("エラーが発生しました").assertIsDisplayed()
        composeTestRule.onNodeWithText("ネットワークエラーが発生しました").assertIsDisplayed()

        // 再取得ボタンが表示されることを確認（画面全体のボタンと、エラーメッセージ内のボタン）
        composeTestRule.onNodeWithText("再取得").assertIsDisplayed()

        // 再取得ボタンをクリック
        composeTestRule.onNodeWithText("再取得").performClick()
        assert(retryFetchCalled) { "再取得ボタンが呼び出されていません" }
    }

    @Test
    fun `位置情報の領域には、位置情報の一覧が正しく表示される`() {
        // テスト用のパラメータを設定
        val cities = listOf("東京", "大阪", "札幌")
        val selectedCity = "東京"
        var citySelected = ""

        // コンポーネントを設定
        composeTestRule.setContent {
            WhetherAppTheme {
                CitySelector(
                    cities = cities,
                    selectedCity = selectedCity,
                    onCitySelected = { citySelected = it }
                )
            }
        }

        // 都市が表示されることを確認
        composeTestRule.onNodeWithText("東京").assertIsDisplayed()
        composeTestRule.onNodeWithText("大阪").assertIsDisplayed()
        composeTestRule.onNodeWithText("札幌").assertIsDisplayed()

        // 都市を選択
        composeTestRule.onNodeWithText("大阪").performClick()
        assert(citySelected == "大阪") { "都市選択が正しく反映されていません" }
    }

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
