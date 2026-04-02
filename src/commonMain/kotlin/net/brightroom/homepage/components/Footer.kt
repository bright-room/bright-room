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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.bsky
import bright_room.generated.resources.github
import bright_room.generated.resources.logo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.app.WindowSizeClass
import net.brightroom.homepage.shared.lib.openUrl
import org.jetbrains.compose.resources.painterResource
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
                        .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                FooterLogo()
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
                        .widthIn(max = 1000.dp)
                        .padding(horizontal = 24.dp, vertical = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    FooterLogo()
                    Spacer(Modifier.height(8.dp))
                    FooterCopy()
                }
                FooterSocials()
            }
        }
    }
}

@Composable
private fun FooterLogo() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "bright-room logo",
            modifier = Modifier.size(28.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "bright-room",
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
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
        text = "\u00A9 $year bright-room. All rights reserved.",
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
