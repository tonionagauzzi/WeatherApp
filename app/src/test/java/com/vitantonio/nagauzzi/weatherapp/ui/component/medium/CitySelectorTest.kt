package com.vitantonio.nagauzzi.weatherapp.ui.component.medium

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitySelectorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `位置情報の領域には、位置情報の一覧が正しく表示される`() {
        // テスト用のパラメータを設定
        val cities = listOf("東京", "大阪", "札幌")
        val selectedCity = "東京"
        var citySelected = ""

        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
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
}
