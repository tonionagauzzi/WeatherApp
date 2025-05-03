package com.vitantonio.nagauzzi.whetherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * 天気情報のViewModelクラス
 */
class WeatherViewModel : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    private val _selectedCity = MutableStateFlow<String>("東京")
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    private val _availableCities = MutableStateFlow<List<String>>(emptyList())
    val availableCities: StateFlow<List<String>> = _availableCities.asStateFlow()

    init {
        loadCities()
        fetchWeatherForSelectedCity()
    }

    /**
     * 都市リストを読み込む
     */
    private fun loadCities() {
        val cities = repository.getDefaultCities()
        _availableCities.value = cities
    }

    /**
     * 選択中の都市の天気を取得する
     */
    private fun fetchWeatherForSelectedCity() {
        _weatherState.value = WeatherUiState.Loading
        viewModelScope.launch {
            repository.getWeatherForCity(_selectedCity.value)
                .catch { error ->
                    _weatherState.value = WeatherUiState.Error(error.message ?: "Unknown error")
                }
                .collect { weather ->
                    _weatherState.value = WeatherUiState.Success(weather)
                }
        }
    }

    /**
     * 都市を選択する
     *
     * @param cityName 選択する都市名
     */
    fun selectCity(cityName: String) {
        _selectedCity.value = cityName
        fetchWeatherForSelectedCity()
    }
}

/**
 * 天気情報のUI状態を表すシールドクラス
 */
sealed class WeatherUiState {
    data object Loading : WeatherUiState()

    data class Success(val weather: Weather) : WeatherUiState()

    data class Error(val message: String) : WeatherUiState()
}
