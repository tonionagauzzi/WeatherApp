package com.vitantonio.nagauzzi.weatherapp.ui.component.small

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(qualifiers = "ja")
@RunWith(AndroidJUnit4::class)
class ErrorMessageTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `エラーメッセージの領域には、エラーメッセージが正しく表示される`() {
        // テスト用の天気データ
        val message = "天気情報の取得に失敗しました"

        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                ErrorMessage(message = message)
            }
        }

        // 天気情報が表示されることを確認
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }
}
