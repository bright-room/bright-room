package net.brightroom.homepage.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.brightroom.homepage.shared.theme.Dimensions

@Composable
fun SectionContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = Dimensions.MaxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.SectionHorizontalPadding)
                    .padding(
                        top = Dimensions.SectionTopPadding,
                        bottom = Dimensions.SectionBottomPadding,
                    ),
            content = content,
        )
    }
}
