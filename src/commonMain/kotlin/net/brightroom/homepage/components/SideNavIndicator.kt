package net.brightroom.homepage.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.VerticalAlignTop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SideNavIndicator(
    activeSection: NavSection,
    navLabels: Map<NavSection, String>,
    onNavClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sections = NavSection.entries
    val activeIndex = sections.indexOf(activeSection)

    Column(
        modifier = modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Up arrow
        IconButton(
            onClick = {
                if (activeIndex > 0) {
                    onNavClick(sections[activeIndex - 1])
                }
            },
            enabled = activeIndex > 0,
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f),
            ),
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Previous section",
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(Modifier.height(4.dp))

        // Dots
        NavSection.entries.forEach { section ->
            val isActive = section == activeSection
            SideNavDot(
                label = navLabels[section] ?: section.id,
                isActive = isActive,
                onClick = { onNavClick(section) },
            )
        }

        Spacer(Modifier.height(4.dp))

        // Down arrow
        IconButton(
            onClick = {
                if (activeIndex < sections.lastIndex) {
                    onNavClick(sections[activeIndex + 1])
                }
            },
            enabled = activeIndex < sections.lastIndex,
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f),
            ),
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Next section",
                modifier = Modifier.size(20.dp),
            )
        }

    }
}

@Composable
fun BackToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        Icon(
            imageVector = Icons.Default.VerticalAlignTop,
            contentDescription = "Back to top",
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun SideNavDot(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    var isHovered by remember { mutableStateOf(false) }

    val dotWidth by animateDpAsState(
        targetValue = if (isActive) 20.dp else 8.dp,
        animationSpec = tween(durationMillis = 200),
    )
    val dotHeight = 8.dp

    val dotColor by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.primary
            isHovered -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        },
        animationSpec = tween(durationMillis = 200),
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            }
            .clickable(
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                onClick = onClick,
            )
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        // Dot (always at the same position)
        Box(
            modifier = Modifier
                .width(dotWidth)
                .height(dotHeight)
                .clip(CircleShape)
                .background(dotColor),
        )

        // Label — measured but reported as zero size so it doesn't push the parent
        if (isHovered) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints.copy(maxWidth = Int.MAX_VALUE))
                        layout(0, 0) {
                            placeable.placeRelative(x = 28.dp.roundToPx(), y = -(placeable.height / 2))
                        }
                    },
            ) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                    color = if (isActive) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                )
            }
        }
    }
}
