package com.vitantonio.nagauzzi.whetherapp.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.ComposeContentTestRule

/**
 * テスト用のユーティリティクラス
 */
object TestUtils {
    /**
     * テスト用のコンポーネントをローカルInspectionモードで設定する
     *
     * @param rule ComposeContentTestRule
     * @param content コンポーネント設定
     */
    fun setContentWithTestContext(rule: ComposeContentTestRule, content: @Composable () -> Unit) {
        rule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true
            ) {
                content()
            }
        }
    }
}
