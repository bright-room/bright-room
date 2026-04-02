package net.brightroom.homepage.screens.roadmap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.roadmap_desc
import bright_room.generated.resources.roadmap_label
import bright_room.generated.resources.roadmap_status_current
import bright_room.generated.resources.roadmap_status_planned
import bright_room.generated.resources.roadmap_status_upcoming
import bright_room.generated.resources.roadmap_title
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.data.RoadmapItem
import net.brightroom.homepage.data.RoadmapStatus
import net.brightroom.homepage.shared.theme.AccentBlue
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoadmapSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val roadmapItems by viewModel.roadmapItems.collectAsState()

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
                label = stringResource(Res.string.roadmap_label),
                title = stringResource(Res.string.roadmap_title),
                description = stringResource(Res.string.roadmap_desc),
            )

            Spacer(Modifier.height(48.dp))

            Column(modifier = Modifier.padding(start = 8.dp)) {
                roadmapItems.forEachIndexed { index, item ->
                    RoadmapTimelineItem(
                        item = item,
                        isLast = index == roadmapItems.lastIndex,
                    )
                }
            }
        }
    }
}

@Composable
private fun RoadmapTimelineItem(
    item: RoadmapItem,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    val dotColor =
        when (item.status) {
            RoadmapStatus.CURRENT -> MaterialTheme.colorScheme.primary
            RoadmapStatus.UPCOMING -> AccentBlue
            RoadmapStatus.PLANNED -> MaterialTheme.colorScheme.outline
        }

    val statusLabel =
        when (item.status) {
            RoadmapStatus.CURRENT -> stringResource(Res.string.roadmap_status_current)
            RoadmapStatus.UPCOMING -> stringResource(Res.string.roadmap_status_upcoming)
            RoadmapStatus.PLANNED -> stringResource(Res.string.roadmap_status_planned)
        }

    val statusColor =
        when (item.status) {
            RoadmapStatus.CURRENT -> MaterialTheme.colorScheme.primary
            RoadmapStatus.UPCOMING -> AccentBlue
            RoadmapStatus.PLANNED -> MaterialTheme.colorScheme.onSurfaceVariant
        }

    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        // Timeline line + dot
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .padding(top = 4.dp)
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(dotColor),
            )
            if (!isLast) {
                Box(
                    modifier =
                        Modifier
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.outline),
                )
            }
        }

        // Content
        Column(
            modifier =
                Modifier
                    .padding(start = 16.dp, bottom = if (isLast) 0.dp else 40.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.quarter,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor,
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = statusLabel.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = statusColor,
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(statusColor.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 2.dp),
                )
            }

            Spacer(Modifier.height(12.dp))

            item.items.forEach { task ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    border = CardDefaults.outlinedCardBorder(),
                ) {
                    Text(
                        text = task,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    )
                }
            }
        }
    }
}
