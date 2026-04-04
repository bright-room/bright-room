package net.brightroom.homepage.screens.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MergeType
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import net.brightroom.homepage.components.EqualHeightFlowRow
import net.brightroom.homepage.components.IconBox
import net.brightroom.homepage.components.SectionContainer
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.components.StandardCard
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

private data class StatItem(
    val icon: ImageVector,
    val label: String,
    val value: String,
)

@Composable
fun StatsSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val stats = viewModel.stats

    SectionContainer(modifier = modifier) {
        SectionHeader(
            label = stringResource(Res.string.stats_label),
            title = stringResource(Res.string.stats_title),
            description = stringResource(Res.string.stats_desc),
        )

        Spacer(Modifier.height(Dimensions.SectionContentSpacing))

        val items =
            listOf(
                StatItem(Icons.Default.Folder, stringResource(Res.string.stats_repositories), stats.repositories.toString()),
                StatItem(Icons.Default.People, stringResource(Res.string.stats_contributors), stats.contributors.toString()),
                StatItem(Icons.Default.Timeline, stringResource(Res.string.stats_total_commits), "${stats.totalCommits}+"),
                StatItem(Icons.AutoMirrored.Filled.MergeType, stringResource(Res.string.stats_open_prs), stats.openPrs.toString()),
                StatItem(Icons.Default.CheckCircle, stringResource(Res.string.stats_closed_issues), stats.closedIssues.toString()),
                StatItem(Icons.Default.Star, stringResource(Res.string.stats_total_stars), stats.totalStars.toString()),
            )

        EqualHeightFlowRow(
            items = items,
            maxItemsInEachRow = 6,
            horizontalSpacing = Dimensions.CardGridSpacingMd,
            verticalSpacing = Dimensions.CardGridSpacingMd,
        ) { item, maxHeight, onHeightMeasured, itemModifier ->
            StatCard(
                item = item,
                maxCardHeight = maxHeight,
                onHeightMeasured = onHeightMeasured,
                modifier = itemModifier.widthIn(min = 160.dp),
            )
        }
    }
}

@Composable
private fun StatCard(
    item: StatItem,
    maxCardHeight: Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    StandardCard(
        modifier = modifier,
        maxHeight = maxCardHeight,
        onHeightMeasured = onHeightMeasured,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconBox(
                icon = item.icon,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                iconTint = MaterialTheme.colorScheme.primary,
                boxSize = 40.dp,
                iconSize = 20.dp,
                cornerRadius = 10.dp,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = item.value,
                fontFamily = FontFamily.Monospace,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.label,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
