package net.brightroom.homepage.screens.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
        modifier = Modifier
            .widthIn(max = 1200.dp)
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

        val items = listOf(
            stringResource(Res.string.stats_repositories) to stats.repositories.toString(),
            stringResource(Res.string.stats_contributors) to stats.contributors.toString(),
            stringResource(Res.string.stats_total_commits) to "${stats.totalCommits}+",
            stringResource(Res.string.stats_open_prs) to stats.openPrs.toString(),
            stringResource(Res.string.stats_closed_issues) to stats.closedIssues.toString(),
            stringResource(Res.string.stats_total_stars) to stats.totalStars.toString(),
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 6,
        ) {
            items.forEach { (label, value) ->
                StatCard(
                    label = label,
                    value = value,
                    modifier = Modifier.weight(1f).widthIn(min = 160.dp, max = 200.dp),
                )
            }
        }
    }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
