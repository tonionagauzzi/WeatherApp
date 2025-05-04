package com.vitantonio.nagauzzi.weatherapp.ui.component.large

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.weatherapp.ui.component.medium.CitySelector
import com.vitantonio.nagauzzi.weatherapp.ui.component.medium.WeatherCard
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.ErrorMessage
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.RetryButton
import com.vitantonio.nagauzzi.weatherapp.viewmodel.WeatherUiState

/**
 * 天気情報を表示するメイン画面
 */
@Composable
fun WeatherScreen(
    weatherState: WeatherUiState,
    selectedCity: String,
    availableCities: List<String>,
    onCitySelected: (String) -> Unit,
    onRetryFetch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "天気情報",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CitySelector(
            cities = availableCities,
            selectedCity = selectedCity,
            onCitySelected = onCitySelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (weatherState) {
            is WeatherUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.padding(16.dp).testTag(tag = "progress_indicator")
            )
            is WeatherUiState.Success -> WeatherCard(weather = weatherState.weather)
            is WeatherUiState.Error -> ErrorMessage(message = weatherState.message)
        }

        if (weatherState !is WeatherUiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            RetryButton(onRetry = onRetryFetch)
        }
    }
}
