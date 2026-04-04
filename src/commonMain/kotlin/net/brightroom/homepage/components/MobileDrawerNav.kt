package net.brightroom.homepage.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.theme_dark_mode
import bright_room.generated.resources.theme_light_mode
import net.brightroom.homepage.app.LocalAppViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun MobileDrawerNav(
    isOpen: Boolean,
    activeSection: NavSection,
    navLabels: Map<NavSection, String>,
    categoryLabels: Map<NavCategory, String>,
    onSectionClick: (NavSection) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetWidth = 300.dp
    val animationProgress by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(300),
    )

    if (isOpen || animationProgress > 0f) {
        // Scrim
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = animationProgress * 0.32f }
                    .background(MaterialTheme.colorScheme.scrim)
                    .clickable(
                        indication = null,
                        interactionSource = null,
                    ) { onDismiss() },
        )

        // Modal Side Sheet
        Surface(
            modifier =
                Modifier
                    .graphicsLayer {
                        translationX = (animationProgress - 1f) * sheetWidth.toPx()
                    }.width(sheetWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount < -20f) onDismiss()
                        }
                    },
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            tonalElevation = 1.dp,
            shadowElevation = 0.dp,
        ) {
            val viewModel = LocalAppViewModel.current

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 24.dp, bottom = 32.dp),
            ) {
                NavCategory.entries.forEach { category ->
                    val categoryLabel = categoryLabels[category] ?: category.name

                    if (category != NavCategory.entries.first()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 28.dp, vertical = 10.dp),
                            color = MaterialTheme.colorScheme.outlineVariant,
                        )
                    }

                    Text(
                        text = categoryLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 8.dp),
                    )

                    category.sections.forEach { section ->
                        val label = navLabels[section] ?: section.id
                        val isActive = section == activeSection

                        DrawerNavItem(
                            label = label,
                            isActive = isActive,
                            onClick = {
                                onSectionClick(section)
                                onDismiss()
                            },
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 10.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )

                // Dark mode toggle
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.toggleTheme() }
                            .padding(horizontal = 28.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Icon(
                        imageVector = if (viewModel.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = stringResource(if (viewModel.isDarkTheme) Res.string.theme_dark_mode else Res.string.theme_light_mode),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                    )
                    Switch(
                        checked = viewModel.isDarkTheme,
                        onCheckedChange = { viewModel.toggleTheme() },
                    )
                }

                // Language toggle
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.toggleLanguage() }
                            .padding(horizontal = 28.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                    // 各言語の自称名は翻訳対象外のため意図的にハードコード
                    Text(
                        text = if (viewModel.isJapanese) "日本語" else "English",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = if (viewModel.isJapanese) "EN" else "JA",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerNavItem(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(50))
                .clickable(onClick = onClick)
                .then(
                    if (isActive) {
                        Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    } else {
                        Modifier
                    },
                ).padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color =
                if (isActive) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
        )
    }
}
