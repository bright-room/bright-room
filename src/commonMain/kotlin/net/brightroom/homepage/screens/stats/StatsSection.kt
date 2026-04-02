package net.brightroom.homepage.screens.stats

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MergeType
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.stats_closed_issues
import bright_room.generated.resources.stats_contributors
import bright_room.generated.resources.stats_desc
import bright_room.generated.resources.stats_label
import bright_room.generated.resources.stats_open_prs
import bright_room.generated.resources.stats_repositories
import bright_room.generated.resources.stats_title
import bright_room.generated.resources.stats_total_commits
import bright_room.generated.resources.stats_total_stars
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.SectionHeader
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatsSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val stats by viewModel.stats.collectAsState()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp, bottom = 60.dp),
        ) {
            SectionHeader(
                label = stringResource(Res.string.stats_label),
                title = stringResource(Res.string.stats_title),
                description = stringResource(Res.string.stats_desc),
            )

            Spacer(Modifier.height(48.dp))

            data class StatItem(val icon: ImageVector, val label: String, val value: String)

            val items =
                listOf(
                    StatItem(Icons.Default.Folder, stringResource(Res.string.stats_repositories), stats.repositories.toString()),
                    StatItem(Icons.Default.People, stringResource(Res.string.stats_contributors), stats.contributors.toString()),
                    StatItem(Icons.Default.Timeline, stringResource(Res.string.stats_total_commits), "${stats.totalCommits}+"),
                    StatItem(Icons.Default.MergeType, stringResource(Res.string.stats_open_prs), stats.openPrs.toString()),
                    StatItem(Icons.Default.CheckCircle, stringResource(Res.string.stats_closed_issues), stats.closedIssues.toString()),
                    StatItem(Icons.Default.Star, stringResource(Res.string.stats_total_stars), stats.totalStars.toString()),
                )

            val density = LocalDensity.current
            var maxCardHeight by remember { mutableStateOf(0.dp) }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 6,
            ) {
                items.forEach { item ->
                    StatCard(
                        icon = item.icon,
                        label = item.label,
                        value = item.value,
                        maxCardHeight = maxCardHeight,
                        onHeightMeasured = { h ->
                            val hDp = with(density) { h.toDp() }
                            if (hDp > maxCardHeight) maxCardHeight = hDp
                        },
                        modifier = Modifier.weight(1f).widthIn(min = 160.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    maxCardHeight: Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .defaultMinSize(minHeight = maxCardHeight)
                .onSizeChanged { onHeightMeasured(it.height) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
