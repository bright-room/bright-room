package net.brightroom.homepage.screens.join

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.join_btn
import bright_room.generated.resources.join_desc
import bright_room.generated.resources.join_label
import bright_room.generated.resources.join_step1_desc
import bright_room.generated.resources.join_step1_title
import bright_room.generated.resources.join_step2_desc
import bright_room.generated.resources.join_step2_title
import bright_room.generated.resources.join_step3_desc
import bright_room.generated.resources.join_step3_title
import bright_room.generated.resources.join_title
import net.brightroom.homepage.components.GitHubButton
import net.brightroom.homepage.components.SectionContainer
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.shared.lib.openUrl
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JoinSection(modifier: Modifier = Modifier) {
    SectionContainer(modifier = modifier) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = Dimensions.CardBorderAlpha)),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.radialGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        Color.Transparent,
                                    ),
                                radius = 600f,
                            ),
                        ),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SectionHeader(
                        label = stringResource(Res.string.join_label),
                        title = stringResource(Res.string.join_title),
                        description = stringResource(Res.string.join_desc),
                        centered = true,
                    )

                    Spacer(Modifier.height(Dimensions.SectionContentSpacing))

                    val steps =
                        listOf(
                            Triple("01", Res.string.join_step1_title, Res.string.join_step1_desc),
                            Triple("02", Res.string.join_step2_title, Res.string.join_step2_desc),
                            Triple("03", Res.string.join_step3_title, Res.string.join_step3_desc),
                        )

                    val density = LocalDensity.current
                    var maxStepHeight by remember { mutableStateOf(0.dp) }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        maxItemsInEachRow = 3,
                    ) {
                        steps.forEach { (num, titleRes, descRes) ->
                            Column(
                                modifier =
                                    Modifier
                                        .widthIn(min = 220.dp, max = 260.dp)
                                        .defaultMinSize(minHeight = maxStepHeight)
                                        .onSizeChanged { size ->
                                            val hDp = with(density) { size.height.toDp() }
                                            if (hDp > maxStepHeight) maxStepHeight = hDp
                                        }.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = num,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = stringResource(titleRes),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = stringResource(descRes),
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 22.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(40.dp))

                    GitHubButton(
                        text = stringResource(Res.string.join_btn),
                        onClick = { openUrl("https://github.com/bright-room") },
                    )
                }
            }
        }
    }
}
