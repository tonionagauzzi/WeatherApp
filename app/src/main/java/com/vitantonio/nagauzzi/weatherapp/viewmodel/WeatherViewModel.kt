package com.vitantonio.nagauzzi.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitantonio.nagauzzi.weatherapp.model.Weather
import com.vitantonio.nagauzzi.weatherapp.repository.WeatherRepository
import com.vitantonio.nagauzzi.weatherapp.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 天気情報のViewModelクラス
 */
class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(),
) : ViewModel() {
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
            repository.getWeatherForCity(_selectedCity.value).onSuccess { weather ->
                _weatherState.value = WeatherUiState.Success(weather)
            }.onFailure { error ->
                _weatherState.value = WeatherUiState.Error(error.message ?: "Unknown error")
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

    /**
     * 天気情報を再取得する
     */
    fun retryFetchWeather() {
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
