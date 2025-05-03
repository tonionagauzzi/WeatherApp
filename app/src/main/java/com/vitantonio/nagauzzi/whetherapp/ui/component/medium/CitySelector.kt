package com.vitantonio.nagauzzi.whetherapp.ui.component.medium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.whetherapp.ui.component.small.CityChip
import com.vitantonio.nagauzzi.whetherapp.ui.theme.WhetherAppTheme

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
