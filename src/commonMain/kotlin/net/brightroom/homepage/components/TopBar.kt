package net.brightroom.homepage.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.brightroom.homepage.app.LocalAppViewModel

enum class NavSection(val id: String) {
    HOME("home"),
    ABOUT("about"),
    STATS("stats"),
    MEMBERS("members"),
    PROJECTS("projects"),
    TECHSTACK("techstack"),
    CONTRIBUTING("contributing"),
    ROADMAP("roadmap"),
    FAQ("faq"),
    JOIN("join"),
}

enum class NavCategory {
    OVERVIEW,
    WORKS,
    PARTICIPATE,
    SUPPORT,
    ;

    val sections: List<NavSection>
        get() =
            when (this) {
                OVERVIEW -> listOf(NavSection.ABOUT, NavSection.STATS, NavSection.MEMBERS)
                WORKS -> listOf(NavSection.PROJECTS, NavSection.TECHSTACK)
                PARTICIPATE -> listOf(NavSection.CONTRIBUTING, NavSection.ROADMAP)
                SUPPORT -> listOf(NavSection.FAQ, NavSection.JOIN)
            }
}

@Composable
fun TopBar(
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = LocalAppViewModel.current

    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse,
            ),
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .widthIn(max = 1000.dp)
                    .padding(horizontal = 24.dp)
                    .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onHomeClick() },
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = pulseAlpha)),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "bright-room",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedIconButton(
                    onClick = { viewModel.toggleTheme() },
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        imageVector = if (viewModel.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle theme",
                        modifier = Modifier.size(18.dp),
                    )
                }

                OutlinedIconButton(
                    onClick = { viewModel.toggleLanguage() },
                    modifier = Modifier.size(36.dp),
                ) {
                    Text(
                        text = if (viewModel.isJapanese) "EN" else "JA",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                    )
                }
            }
        }
    }
}
