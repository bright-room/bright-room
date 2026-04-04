package net.brightroom.homepage.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.logo
import net.brightroom.homepage.app.LocalAppViewModel
import org.jetbrains.compose.resources.painterResource

enum class NavSection(val id: String) {
    HOME("home"),
    ABOUT("about"),
    STATS("stats"),
    MEMBERS("members"),
    PROJECTS("projects"),
    TECHSTACK("techstack"),
    CONTRIBUTING("contributing"),
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
                PARTICIPATE -> listOf(NavSection.CONTRIBUTING)
                SUPPORT -> listOf(NavSection.FAQ, NavSection.JOIN)
            }
}

@Composable
fun TopBar(
    onHomeClick: () -> Unit,
    isCompact: Boolean = false,
    showControls: Boolean = false,
    isMenuOpen: Boolean = false,
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val viewModel = LocalAppViewModel.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        if (isCompact) {
            // Mobile: hamburger | centered title | controls
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 8.dp),
            ) {
                // Left: Hamburger / Close menu
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.align(Alignment.CenterStart).size(40.dp),
                ) {
                    Icon(
                        imageVector = if (isMenuOpen) Icons.AutoMirrored.Filled.MenuOpen else Icons.Default.Menu,
                        contentDescription = if (isMenuOpen) "Close menu" else "Open menu",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp),
                    )
                }

                // Center: Logo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center).clickable { onHomeClick() },
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "bright-room logo",
                        modifier = Modifier.size(32.dp),
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
            }
        } else {
            // Default: logo left, optional controls right
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onHomeClick() },
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "bright-room logo",
                        modifier = Modifier.size(32.dp),
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

                if (showControls) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { viewModel.toggleTheme() },
                            modifier = Modifier.size(40.dp),
                            colors =
                                IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                        ) {
                            Icon(
                                imageVector = if (viewModel.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle theme",
                                modifier = Modifier.size(20.dp),
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleLanguage() },
                            modifier = Modifier.size(40.dp),
                            colors =
                                IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                        ) {
                            Text(
                                text = if (viewModel.isJapanese) "EN" else "JA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}
