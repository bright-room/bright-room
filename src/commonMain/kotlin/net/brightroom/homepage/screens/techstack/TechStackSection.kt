package net.brightroom.homepage.screens.techstack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.tech_cat_all
import bright_room.generated.resources.tech_cat_backend
import bright_room.generated.resources.tech_cat_database
import bright_room.generated.resources.tech_cat_frontend
import bright_room.generated.resources.tech_cat_infra
import bright_room.generated.resources.tech_cat_language
import bright_room.generated.resources.tech_desc
import bright_room.generated.resources.tech_label
import bright_room.generated.resources.tech_title
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.EqualHeightFlowRow
import net.brightroom.homepage.components.SectionContainer
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.components.StandardCard
import net.brightroom.homepage.data.TechItemData
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

@Composable
private fun resolveCategoryLabel(labelKey: String): String =
    when (labelKey) {
        "tech_cat_language" -> stringResource(Res.string.tech_cat_language)
        "tech_cat_frontend" -> stringResource(Res.string.tech_cat_frontend)
        "tech_cat_backend" -> stringResource(Res.string.tech_cat_backend)
        "tech_cat_database" -> stringResource(Res.string.tech_cat_database)
        "tech_cat_infra" -> stringResource(Res.string.tech_cat_infra)
        else -> labelKey
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TechStackSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val techStack = viewModel.techStack
    var selectedCategory by remember { mutableStateOf("ALL") }

    val filteredItems =
        if (selectedCategory == "ALL") {
            techStack.items
        } else {
            techStack.items.filter { it.category == selectedCategory }
        }

    SectionContainer(modifier = modifier) {
        SectionHeader(
            label = stringResource(Res.string.tech_label),
            title = stringResource(Res.string.tech_title),
            description = stringResource(Res.string.tech_desc),
        )

        Spacer(Modifier.height(32.dp))

        // Filter chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val filterChipColors =
                FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                )
            FilterChip(
                selected = selectedCategory == "ALL",
                onClick = { selectedCategory = "ALL" },
                label = { Text(stringResource(Res.string.tech_cat_all)) },
                shape = RoundedCornerShape(100.dp),
                colors = filterChipColors,
            )
            techStack.categories.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category.id,
                    onClick = { selectedCategory = category.id },
                    label = { Text(resolveCategoryLabel(category.labelKey)) },
                    shape = RoundedCornerShape(100.dp),
                    colors = filterChipColors,
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Tech grid
        EqualHeightFlowRow(
            items = filteredItems,
            maxItemsInEachRow = 6,
            horizontalSpacing = Dimensions.CardGridSpacingSm,
            verticalSpacing = Dimensions.CardGridSpacingSm,
        ) { item, maxHeight, onHeightMeasured, _ ->
            TechCard(
                item = item,
                maxCardHeight = maxHeight,
                onHeightMeasured = onHeightMeasured,
                modifier = Modifier.width(160.dp),
            )
        }
    }
}

@Composable
private fun TechCard(
    item: TechItemData,
    maxCardHeight: Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val fallbackColor = MaterialTheme.colorScheme.primary
    val color =
        remember(item.color) {
            try {
                Color(("FF" + item.color.removePrefix("#")).toLong(16))
            } catch (_: Exception) {
                fallbackColor
            }
        }

    StandardCard(
        modifier = modifier,
        maxHeight = maxCardHeight,
        onHeightMeasured = onHeightMeasured,
        shape = RoundedCornerShape(12.dp),
        contentPadding = 20.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.category.lowercase(),
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
