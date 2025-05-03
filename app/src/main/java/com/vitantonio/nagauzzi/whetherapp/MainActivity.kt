package com.vitantonio.nagauzzi.whetherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.whetherapp.ui.component.WeatherScreen
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme
import com.vitantonio.nagauzzi.whetherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhetherAppTheme {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhetherAppTheme {
        Greeting("Android")
    }
}
