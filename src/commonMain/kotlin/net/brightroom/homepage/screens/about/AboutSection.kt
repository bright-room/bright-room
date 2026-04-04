package net.brightroom.homepage.screens.about

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.about_card_community_desc
import bright_room.generated.resources.about_card_community_title
import bright_room.generated.resources.about_card_oss_desc
import bright_room.generated.resources.about_card_oss_title
import bright_room.generated.resources.about_card_products_desc
import bright_room.generated.resources.about_card_products_title
import bright_room.generated.resources.about_desc
import bright_room.generated.resources.about_label
import bright_room.generated.resources.about_title
import net.brightroom.homepage.components.EqualHeightFlowRow
import net.brightroom.homepage.components.IconBox
import net.brightroom.homepage.components.SectionContainer
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.components.StandardCard
import net.brightroom.homepage.shared.theme.AccentBlue
import net.brightroom.homepage.shared.theme.AccentPink
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

private data class AboutCardData(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val iconBackground: Color,
    val iconTint: Color,
)

@Composable
fun AboutSection(modifier: Modifier = Modifier) {
    SectionContainer(modifier = modifier) {
        SectionHeader(
            label = stringResource(Res.string.about_label),
            title = stringResource(Res.string.about_title),
            description = stringResource(Res.string.about_desc),
        )

        Spacer(Modifier.height(Dimensions.SectionContentSpacing))

        val cards =
            listOf(
                AboutCardData(
                    icon = Icons.Default.Code,
                    title = stringResource(Res.string.about_card_oss_title),
                    description = stringResource(Res.string.about_card_oss_desc),
                    iconBackground = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    iconTint = MaterialTheme.colorScheme.primary,
                ),
                AboutCardData(
                    icon = Icons.Default.Groups,
                    title = stringResource(Res.string.about_card_community_title),
                    description = stringResource(Res.string.about_card_community_desc),
                    iconBackground = AccentBlue.copy(alpha = 0.1f),
                    iconTint = AccentBlue,
                ),
                AboutCardData(
                    icon = Icons.Default.Rocket,
                    title = stringResource(Res.string.about_card_products_title),
                    description = stringResource(Res.string.about_card_products_desc),
                    iconBackground = AccentPink.copy(alpha = 0.1f),
                    iconTint = AccentPink,
                ),
            )

        EqualHeightFlowRow(
            items = cards,
            maxItemsInEachRow = 3,
            horizontalSpacing = Dimensions.CardGridSpacingLg,
            verticalSpacing = Dimensions.CardGridSpacingLg,
        ) { card, maxHeight, onHeightMeasured, itemModifier ->
            AboutCard(
                card = card,
                maxCardHeight = maxHeight,
                onHeightMeasured = onHeightMeasured,
                modifier = itemModifier.widthIn(min = 280.dp),
            )
        }
    }
}

@Composable
private fun AboutCard(
    card: AboutCardData,
    maxCardHeight: androidx.compose.ui.unit.Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    StandardCard(
        modifier = modifier,
        maxHeight = maxCardHeight,
        onHeightMeasured = onHeightMeasured,
        contentPadding = Dimensions.CardInnerPaddingLarge,
    ) {
        IconBox(
            icon = card.icon,
            backgroundColor = card.iconBackground,
            iconTint = card.iconTint,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = card.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = card.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp,
        )
    }
}
