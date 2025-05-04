package com.vitantonio.nagauzzi.weatherapp.ui.component.medium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.weatherapp.R
import com.vitantonio.nagauzzi.weatherapp.ui.component.small.CityChip
import com.vitantonio.nagauzzi.weatherapp.ui.theme.WeatherAppTheme

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

@Preview(locale = "ja", showBackground = true)
@Composable
fun CitySelectorPreview() {
    WeatherAppTheme {
        val context = LocalContext.current
        val fukuoka = context.getString(R.string.label_fukuoka)
        val nagoya = context.getString(R.string.label_nagoya)
        val osaka = context.getString(R.string.label_osaka)
        val sapporo = context.getString(R.string.label_sapporo)
        val tokyo = context.getString(R.string.label_tokyo)
        CitySelector(
            cities = listOf(tokyo, osaka, sapporo, fukuoka, nagoya),
            selectedCity = tokyo,
            onCitySelected = {}
        )
    }
}
