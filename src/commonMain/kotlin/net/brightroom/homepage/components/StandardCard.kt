package net.brightroom.homepage.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.brightroom.homepage.shared.theme.Dimensions

@Composable
fun StandardCard(
    modifier: Modifier = Modifier,
    maxHeight: Dp = 0.dp,
    onHeightMeasured: (Int) -> Unit = {},
    onClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(Dimensions.CardCornerRadius),
    contentPadding: Dp = Dimensions.CardInnerPadding,
    content: @Composable ColumnScope.() -> Unit,
) {
    val cardModifier =
        modifier
            .defaultMinSize(minHeight = maxHeight)
            .onSizeChanged { onHeightMeasured(it.height) }
            .hoverFloat(shape = shape)
    val cardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    val cardBorder = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = Dimensions.CardBorderAlpha))

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = cardModifier,
            shape = shape,
            colors = cardColors,
            border = cardBorder,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    } else {
        Card(
            modifier = cardModifier,
            shape = shape,
            colors = cardColors,
            border = cardBorder,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    }
}
