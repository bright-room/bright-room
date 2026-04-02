package net.brightroom.homepage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class NavRailItemData(
    val category: NavCategory,
    val icon: ImageVector,
)

private val navRailItems =
    listOf(
        NavRailItemData(NavCategory.OVERVIEW, Icons.Default.Info),
        NavRailItemData(NavCategory.WORKS, Icons.Default.Code),
        NavRailItemData(NavCategory.PARTICIPATE, Icons.Default.Groups),
        NavRailItemData(NavCategory.SUPPORT, Icons.Default.Support),
    )

@Composable
fun NavigationRailNav(
    activeSection: NavSection,
    categoryLabels: Map<NavCategory, String>,
    hoveredCategory: NavCategory?,
    onHoveredCategoryChange: (NavCategory?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activeCategory = NavCategory.entries.firstOrNull { category ->
        activeSection in category.sections
    }

    NavigationRail(
        modifier = modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Exit) {
                        onHoveredCategoryChange(null)
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
        contentColor = MaterialTheme.colorScheme.onBackground,
        header = {
            Spacer(Modifier.height(8.dp))
        },
    ) {
        Spacer(Modifier.weight(1f))

        navRailItems.forEach { item ->
            val isSelected = item.category == activeCategory

            Box(
                modifier = Modifier.pointerInput(item.category) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.type == PointerEventType.Enter) {
                                onHoveredCategoryChange(item.category)
                            }
                        }
                    }
                },
            ) {
                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        if (hoveredCategory == item.category) {
                            onHoveredCategoryChange(null)
                        } else {
                            onHoveredCategoryChange(item.category)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = categoryLabels[item.category],
                        )
                    },
                    label = {
                        Text(
                            text = categoryLabels[item.category] ?: item.category.name,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1,
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }

        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun NavigationRailFlyout(
    category: NavCategory,
    categoryLabel: String,
    navLabels: Map<NavSection, String>,
    activeSection: NavSection,
    onSectionClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.33f),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 24.dp, bottom = 16.dp),
        ) {
            Text(
                text = categoryLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            )

            category.sections.forEach { section ->
                val isActive = section == activeSection

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSectionClick(section) }
                        .then(
                            if (isActive) {
                                Modifier.background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                )
                            } else {
                                Modifier
                            },
                        )
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                ) {
                    Text(
                        text = navLabels[section] ?: section.id,
                        fontSize = 15.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        color = if (isActive) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                    )
                }
            }
        }
    }
}
