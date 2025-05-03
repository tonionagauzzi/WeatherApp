package com.vitantonio.nagauzzi.whetherapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitantonio.nagauzzi.whetherapp.model.Weather
import com.vitantonio.nagauzzi.whetherapp.model.WeatherCondition
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme
import com.vitantonio.nagauzzi.whetherapp.viewmodel.WeatherUiState

/**
 * 天気情報を表示するメイン画面
 */
@Composable
fun WeatherScreen(
    weatherState: WeatherUiState,
    selectedCity: String,
    availableCities: List<String>,
    onCitySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
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
            is WeatherUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is WeatherUiState.Success -> WeatherCard(weather = weatherState.weather)
            is WeatherUiState.Error -> ErrorMessage(message = weatherState.message)
        }
    }
}

/**
 * 都市選択用のコンポーネント
 */
@Composable
fun CitySelector(
    cities: List<String>,
    selectedCity: String,
    onCitySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cities) { city ->
            CityChip(
                cityName = city,
                isSelected = city == selectedCity,
                onClick = { onCitySelected(city) }
            )
        }
    }
}

/**
 * 都市選択用のチップコンポーネント
 */
@Composable
fun CityChip(
    cityName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = cityName,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * 天気情報カード
 */
@Composable
fun WeatherCard(weather: Weather, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WeatherConditionIcon(
                condition = weather.condition,
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${weather.maxTemperature}°",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${weather.minTemperature}°",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "最高",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "最低",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weather.condition.toJapanese(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    emoji = "💧",
                    label = "湿度",
                    value = "${weather.humidity}%"
                )

                WeatherDetailItem(
                    emoji = "🌬️",
                    label = "風速",
                    value = "${weather.windSpeed} m/s"
                )
            }
        }
    }
}

/**
 * 天気の詳細情報アイテム
 */
@Composable
fun WeatherDetailItem(
    emoji: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * 天気の状態アイコン
 */
@Composable
fun WeatherConditionIcon(condition: WeatherCondition, modifier: Modifier = Modifier) {
    val emoji = when (condition) {
        WeatherCondition.SUNNY -> "☀️"
        WeatherCondition.CLOUDY -> "☁️"
        WeatherCondition.RAINY -> "🌧️"
        WeatherCondition.SNOWY -> "❄️"
        WeatherCondition.STORMY -> "⚡"
    }

    Text(
        text = emoji,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * エラーメッセージ表示
 */
@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp)
    ) {
        Text(
            text = "⚠️",
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "エラーが発生しました",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 天気の状態を日本語に変換する拡張関数
 */
fun WeatherCondition.toJapanese(): String {
    return when (this) {
        WeatherCondition.SUNNY -> "晴れ"
        WeatherCondition.CLOUDY -> "曇り"
        WeatherCondition.RAINY -> "雨"
        WeatherCondition.SNOWY -> "雪"
        WeatherCondition.STORMY -> "嵐"
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    WhetherAppTheme {
        WeatherCard(
            weather = Weather(
                city = "東京",
                maxTemperature = 28,
                minTemperature = 21,
                condition = WeatherCondition.SUNNY,
                humidity = 60,
                windSpeed = 3.5
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CitySelectorPreview() {
    WhetherAppTheme {
        CitySelector(
            cities = listOf("東京", "大阪", "札幌", "福岡", "名古屋"),
            selectedCity = "東京",
            onCitySelected = {}
        )
    }
}
