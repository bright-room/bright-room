package net.brightroom.homepage.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bright_room.generated.resources.Res
import bright_room.generated.resources.a11y_close_menu
import bright_room.generated.resources.a11y_open_menu
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

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
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        if (isCompact) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimensions.TopBarHeight)
                        .padding(horizontal = 8.dp),
            ) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.align(Alignment.CenterStart).size(40.dp),
                ) {
                    Icon(
                        imageVector = if (isMenuOpen) Icons.AutoMirrored.Filled.MenuOpen else Icons.Default.Menu,
                        contentDescription = stringResource(if (isMenuOpen) Res.string.a11y_close_menu else Res.string.a11y_open_menu),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp),
                    )
                }

                LogoText(
                    modifier = Modifier.align(Alignment.Center).clickable { onHomeClick() },
                )
            }
        } else {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .widthIn(max = Dimensions.MaxContentWidth)
                        .padding(horizontal = Dimensions.SectionHorizontalPadding)
                        .height(Dimensions.TopBarHeight),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LogoText(
                    modifier = Modifier.clickable { onHomeClick() },
                )

                if (showControls) {
                    ThemeLanguageControls(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                    )
                }
            }
        }
    }
}
