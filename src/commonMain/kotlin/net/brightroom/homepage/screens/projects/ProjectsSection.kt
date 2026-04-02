package net.brightroom.homepage.screens.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.project_beacon_api_desc
import bright_room.generated.resources.project_bright_cms_desc
import bright_room.generated.resources.project_luminous_cli_desc
import bright_room.generated.resources.project_room_ui_desc
import bright_room.generated.resources.projects_desc
import bright_room.generated.resources.projects_label
import bright_room.generated.resources.projects_title
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.data.ProjectData
import net.brightroom.homepage.shared.lib.openUrl
import org.jetbrains.compose.resources.stringResource

@Composable
private fun resolveProjectDesc(descKey: String): String =
    when (descKey) {
        "project_bright_cms_desc" -> stringResource(Res.string.project_bright_cms_desc)
        "project_room_ui_desc" -> stringResource(Res.string.project_room_ui_desc)
        "project_luminous_cli_desc" -> stringResource(Res.string.project_luminous_cli_desc)
        "project_beacon_api_desc" -> stringResource(Res.string.project_beacon_api_desc)
        else -> descKey
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectsSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val projects by viewModel.projects.collectAsState()

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
                label = stringResource(Res.string.projects_label),
                title = stringResource(Res.string.projects_title),
                description = stringResource(Res.string.projects_desc),
            )

            Spacer(Modifier.height(48.dp))

            val density = LocalDensity.current
            var maxCardHeight by remember { mutableStateOf(0.dp) }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                maxItemsInEachRow = 2,
            ) {
                projects.forEach { project ->
                    ProjectCard(
                        project = project,
                        maxCardHeight = maxCardHeight,
                        onHeightMeasured = { h ->
                            val hDp = with(density) { h.toDp() }
                            if (hDp > maxCardHeight) maxCardHeight = hDp
                        },
                        modifier = Modifier.weight(1f).widthIn(min = 280.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectCard(
    project: ProjectData,
    maxCardHeight: androidx.compose.ui.unit.Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { openUrl(project.githubUrl) },
        modifier =
            modifier
                .defaultMinSize(minHeight = maxCardHeight)
                .onSizeChanged { onHeightMeasured(it.height) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = project.name,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                if (project.stars > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = "${project.stars}",
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = resolveProjectDesc(project.descriptionKey),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp,
            )

            Spacer(Modifier.height(18.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                project.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = tag,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                            )
                        },
                        shape = RoundedCornerShape(100.dp),
                    )
                }
            }
        }
    }
}
