package net.brightroom.homepage.screens.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.project_endpoint_gate_desc
import bright_room.generated.resources.project_idem_desc
import bright_room.generated.resources.project_uniso_desc
import bright_room.generated.resources.projects_desc
import bright_room.generated.resources.projects_label
import bright_room.generated.resources.projects_title
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.EqualHeightFlowRow
import net.brightroom.homepage.components.SectionContainer
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.components.StandardCard
import net.brightroom.homepage.data.ProjectData
import net.brightroom.homepage.shared.lib.openUrl
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

@Composable
private fun resolveProjectDesc(descKey: String): String =
    when (descKey) {
        "project_idem_desc" -> stringResource(Res.string.project_idem_desc)
        "project_uniso_desc" -> stringResource(Res.string.project_uniso_desc)
        "project_endpoint_gate_desc" -> stringResource(Res.string.project_endpoint_gate_desc)
        else -> descKey // strings.xml にキーが未定義の場合はキー名をそのまま表示
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectsSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val projects = viewModel.projects

    SectionContainer(modifier = modifier) {
        SectionHeader(
            label = stringResource(Res.string.projects_label),
            title = stringResource(Res.string.projects_title),
            description = stringResource(Res.string.projects_desc),
        )

        Spacer(Modifier.height(Dimensions.SectionContentSpacing))

        EqualHeightFlowRow(
            items = projects,
            maxItemsInEachRow = 2,
            horizontalSpacing = Dimensions.CardGridSpacingLg,
            verticalSpacing = Dimensions.CardGridSpacingLg,
        ) { project, maxHeight, onHeightMeasured, itemModifier ->
            ProjectCard(
                project = project,
                maxCardHeight = maxHeight,
                onHeightMeasured = onHeightMeasured,
                modifier = itemModifier.widthIn(min = 280.dp),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectCard(
    project: ProjectData,
    maxCardHeight: Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    StandardCard(
        modifier = modifier,
        maxHeight = maxCardHeight,
        onHeightMeasured = onHeightMeasured,
        onClick = { openUrl(project.githubUrl) },
    ) {
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
