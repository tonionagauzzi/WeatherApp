package com.vitantonio.nagauzzi.weatherapp.ui.component.small

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(qualifiers = "ja")
@RunWith(AndroidJUnit4::class)
class RetryButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `再取得ボタンを押すと、再取得処理が行われる`() {
        // テスト用の天気データ
        var retried = false

        // コンポーネントを設定
        composeTestRule.setContent {
            WeatherAppTheme {
                RetryButton(onRetry = {
                    retried = true
                })
            }
        }

        // 再取得ボタンをクリック
        composeTestRule.onNodeWithText("再取得").performClick()
        assert(retried) { "再取得ボタンが呼び出されていません" }
    }
}
