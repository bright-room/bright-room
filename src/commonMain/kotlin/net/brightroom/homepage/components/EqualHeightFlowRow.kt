package net.brightroom.homepage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> EqualHeightFlowRow(
    items: List<T>,
    maxItemsInEachRow: Int,
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    modifier: Modifier = Modifier,
    itemContent: @Composable (item: T, maxHeight: Dp, onHeightMeasured: (Int) -> Unit, itemModifier: Modifier) -> Unit,
) {
    val density = LocalDensity.current
    var maxCardHeight by remember { mutableStateOf(0.dp) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        maxItemsInEachRow = maxItemsInEachRow,
        modifier = modifier,
    ) {
        items.forEach { item ->
            itemContent(
                item,
                maxCardHeight,
                { h ->
                    val hDp = with(density) { h.toDp() }
                    if (hDp > maxCardHeight) maxCardHeight = hDp
                },
                Modifier.weight(1f),
            )
        }
    }
}
