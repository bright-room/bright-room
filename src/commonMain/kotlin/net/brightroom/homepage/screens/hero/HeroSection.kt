package net.brightroom.homepage.screens.hero

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.hero_sub
import bright_room.generated.resources.hero_tagline
import bright_room.generated.resources.hero_title_1
import bright_room.generated.resources.hero_title_2
import bright_room.generated.resources.join_cta
import bright_room.generated.resources.view_on_github
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.app.WindowSizeClass
import net.brightroom.homepage.components.GitHubButton
import net.brightroom.homepage.shared.lib.openUrl
import net.brightroom.homepage.shared.theme.AccentGreen
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroSection(
    onJoinClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = LocalAppViewModel.current
    val titleSize =
        when (viewModel.windowSizeClass) {
            WindowSizeClass.COMPACT -> 40.sp
            WindowSizeClass.MEDIUM -> 56.sp
            WindowSizeClass.EXPANDED, WindowSizeClass.WIDE -> 72.sp
        }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse,
            ),
    )

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = viewModel.windowHeightDp)
                    .background(
                        Brush.radialGradient(
                            colors =
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    Color.Transparent,
                                ),
                            radius = 800f,
                        ),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier =
                    Modifier
                        .widthIn(max = Dimensions.MaxContentWidth)
                        .padding(horizontal = Dimensions.SectionHorizontalPadding)
                        .padding(top = 120.dp, bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Badge
                Row(
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(AccentGreen.copy(alpha = pulseAlpha)),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(Res.string.hero_tagline),
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(Modifier.height(32.dp))

                // Title
                Text(
                    text =
                        buildAnnotatedString {
                            append(stringResource(Res.string.hero_title_1))
                            append("\n")
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(stringResource(Res.string.hero_title_2))
                            }
                        },
                    fontSize = titleSize,
                    fontWeight = FontWeight.Bold,
                    lineHeight = titleSize * 1.05,
                    letterSpacing = (-2).sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 800.dp),
                )

                Spacer(Modifier.height(24.dp))

                // Subtitle
                Text(
                    text = stringResource(Res.string.hero_sub),
                    fontSize = if (viewModel.windowSizeClass == WindowSizeClass.COMPACT) 16.sp else 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 560.dp),
                )

                Spacer(Modifier.height(48.dp))

                // CTA buttons
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    GitHubButton(
                        text = stringResource(Res.string.view_on_github),
                        onClick = { openUrl("https://github.com/bright-room") },
                    )

                    OutlinedButton(
                        onClick = onJoinClick,
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.join_cta),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
