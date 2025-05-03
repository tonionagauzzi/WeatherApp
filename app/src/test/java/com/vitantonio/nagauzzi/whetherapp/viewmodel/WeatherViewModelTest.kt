package com.vitantonio.nagauzzi.whetherapp.viewmodel

import com.vitantonio.nagauzzi.whetherapp.repository.MockWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private lateinit var mockRepository: MockWeatherRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = MockWeatherRepository()

        // テスト対象のViewModelを初期化
        viewModel = WeatherViewModel(mockRepository)

        // テストディスパッチャーの処理を進める
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `初期化時に東京の天気が取得される`() = runTest {
        // 初期状態は東京が選択されているはず
        assertEquals("東京", viewModel.selectedCity.value)

        // 成功状態になっていることを検証
        val weatherState = viewModel.weatherState.value
        assertTrue(weatherState is WeatherUiState.Success)

        // 東京の天気データが正しく取得されているか検証
        if (weatherState is WeatherUiState.Success) {
            val weather = weatherState.weather
            assertEquals("東京", weather.city)
            assertEquals(25, weather.temperature)
            assertEquals(28, weather.maxTemperature)
            assertEquals(21, weather.minTemperature)
        }
    }

    @Test
    fun `都市を選択すると対応する天気が取得される`() = runTest {
        // 大阪を選択
        viewModel.selectCity("大阪")

        // テストディスパッチャーの処理を進める
        testDispatcher.scheduler.advanceUntilIdle()

        // 大阪が選択されているはず
        assertEquals("大阪", viewModel.selectedCity.value)

        // 成功状態になっていることを検証
        val weatherState = viewModel.weatherState.value
        assertTrue(weatherState is WeatherUiState.Success)

        // 大阪の天気データが正しく取得されているか検証
        if (weatherState is WeatherUiState.Success) {
            val weather = weatherState.weather
            assertEquals("大阪", weather.city)
            assertEquals(27, weather.temperature)
            assertEquals(30, weather.maxTemperature)
            assertEquals(23, weather.minTemperature)
        }
    }

    @Test
    fun `エラー発生時にエラー状態になる`() = runTest {
        // モックでエラーを発生させる設定
        mockRepository.shouldReturnError = true

        // テスト対象のViewModelを初期化
        viewModel = WeatherViewModel(mockRepository)

        // テストディスパッチャーの処理を進める
        testDispatcher.scheduler.advanceUntilIdle()

        // エラー状態になっていることを検証
        val weatherState = viewModel.weatherState.value
        assertTrue(weatherState is WeatherUiState.Error)
    }

    @Test
    fun `デフォルト都市リストが取得できる`() = runTest {
        // テスト対象のViewModelを初期化
        viewModel = WeatherViewModel(mockRepository)

        // テストディスパッチャーの処理を進める
        testDispatcher.scheduler.advanceUntilIdle()

        // 正しい都市リストが設定されているか検証
        val cities = viewModel.availableCities.value
        assertEquals(3, cities.size)
        assertTrue(cities.contains("東京"))
        assertTrue(cities.contains("大阪"))
        assertTrue(cities.contains("テスト都市"))
    }
}
