package com.vitantonio.nagauzzi.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.weatherapp.ui.component.large.WeatherScreen
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme
import com.vitantonio.nagauzzi.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val weatherState by viewModel.weatherState.collectAsState()
                val selectedCity by viewModel.selectedCity.collectAsState()
                val availableCities by viewModel.availableCities.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(
                        weatherState = weatherState,
                        selectedCity = selectedCity,
                        availableCities = availableCities,
                        onCitySelected = viewModel::selectCity,
                        onRetryFetch = viewModel::retryFetchWeather,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
