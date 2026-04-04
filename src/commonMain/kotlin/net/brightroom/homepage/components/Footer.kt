package net.brightroom.homepage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.bsky
import bright_room.generated.resources.footer_copy
import bright_room.generated.resources.github
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.app.WindowSizeClass
import net.brightroom.homepage.shared.lib.openUrl
import net.brightroom.homepage.shared.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

@Composable
fun Footer(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val isCompact = viewModel.windowSizeClass == WindowSizeClass.COMPACT

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        if (isCompact) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.SectionHorizontalPadding, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LogoText(
                    logoSize = 28.dp,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(8.dp))
                FooterCopy()
                Spacer(Modifier.height(24.dp))
                FooterSocials()
            }
        } else {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .widthIn(max = Dimensions.MaxContentWidth)
                        .padding(horizontal = Dimensions.SectionHorizontalPadding, vertical = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    LogoText(
                        logoSize = 28.dp,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(8.dp))
                    FooterCopy()
                }
                FooterSocials()
            }
        }
    }
}

@Composable
private fun FooterCopy() {
    val year =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .year
    Text(
        text = stringResource(Res.string.footer_copy, year),
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun FooterSocials() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        IconButton(
            onClick = { openUrl("https://github.com/bright-room") },
            modifier = Modifier.size(40.dp),
        ) {
            Image(
                painter = painterResource(Res.drawable.github),
                contentDescription = "GitHub",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
        IconButton(
            onClick = { openUrl("https://bsky.app/profile/bright-room.net") },
            modifier = Modifier.size(40.dp),
        ) {
            Image(
                painter = painterResource(Res.drawable.bsky),
                contentDescription = "Bluesky",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
    }
}
