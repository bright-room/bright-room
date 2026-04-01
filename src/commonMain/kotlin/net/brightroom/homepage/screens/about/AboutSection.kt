package net.brightroom.homepage.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.shared.theme.AccentBlue
import net.brightroom.homepage.shared.theme.AccentPink
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 1200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp, bottom = 60.dp),
        ) {
            SectionHeader(
                label = stringResource(Res.string.about_label),
                title = stringResource(Res.string.about_title),
                description = stringResource(Res.string.about_desc),
            )

            Spacer(Modifier.height(48.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                maxItemsInEachRow = 3,
            ) {
                val cards =
                    listOf(
                        Triple(
                            stringResource(Res.string.about_card_oss_title),
                            stringResource(Res.string.about_card_oss_desc),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        ),
                        Triple(
                            stringResource(Res.string.about_card_community_title),
                            stringResource(Res.string.about_card_community_desc),
                            AccentBlue.copy(alpha = 0.1f),
                        ),
                        Triple(
                            stringResource(Res.string.about_card_products_title),
                            stringResource(Res.string.about_card_products_desc),
                            AccentPink.copy(alpha = 0.1f),
                        ),
                    )

                cards.forEach { (title, desc, iconBg) ->
                    AboutCard(
                        title = title,
                        description = desc,
                        iconBackground = iconBg,
                        modifier = Modifier.weight(1f).widthIn(min = 280.dp, max = 380.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun AboutCard(
    title: String,
    description: String,
    iconBackground: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .padding(bottom = 20.dp),
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp,
            )
        }
    }
}
