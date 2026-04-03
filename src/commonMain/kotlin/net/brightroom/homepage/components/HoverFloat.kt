package net.brightroom.homepage.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp

enum class HoverHighlight {
    BORDER,
    GLOW,
    NONE,
}

fun Modifier.hoverFloat(
    shape: Shape = RoundedCornerShape(16.dp),
    highlight: HoverHighlight = HoverHighlight.BORDER,
): Modifier =
    composed {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()

        val translationY by animateFloatAsState(
            targetValue = if (isHovered) -8f else 0f,
            animationSpec = tween(durationMillis = 200),
        )
        val shadowElevation by animateFloatAsState(
            targetValue = if (isHovered) 12f else 0f,
            animationSpec = tween(durationMillis = 200),
        )

        val base =
            this
                .hoverable(interactionSource)
                .graphicsLayer {
                    this.translationY = translationY
                    this.shadowElevation = shadowElevation
                    this.shape = shape
                    clip = false
                }

        when (highlight) {
            HoverHighlight.BORDER -> {
                val borderAlpha by animateFloatAsState(
                    targetValue = if (isHovered) 1f else 0f,
                    animationSpec = tween(durationMillis = 200),
                )
                val highlightColor = lerp(
                    Color.Transparent,
                    MaterialTheme.colorScheme.outlineVariant,
                    borderAlpha,
                )
                base.border(
                    width = 1.dp,
                    color = highlightColor,
                    shape = shape,
                )
            }

            HoverHighlight.GLOW -> {
                val glowElevation by animateDpAsState(
                    targetValue = if (isHovered) 16.dp else 0.dp,
                    animationSpec = tween(durationMillis = 200),
                )
                val glowColor = MaterialTheme.colorScheme.primary
                base.shadow(
                    elevation = glowElevation,
                    shape = shape,
                    ambientColor = glowColor,
                    spotColor = glowColor,
                    clip = false,
                )
            }

            HoverHighlight.NONE -> base
        }
    }
