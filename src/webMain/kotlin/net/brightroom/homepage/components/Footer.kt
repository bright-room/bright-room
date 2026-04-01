package net.brightroom.homepage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.footer_copy
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.app.WindowSizeClass
import net.brightroom.homepage.shared.lib.openUrl
import org.jetbrains.compose.resources.stringResource

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
                        .widthIn(max = 1200.dp)
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
        Box(
            modifier =
                Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
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
    Text(
        text = stringResource(Res.string.footer_copy),
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun FooterSocials() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedIconButton(
            onClick = { openUrl("https://github.com/bright-room") },
            modifier = Modifier.size(40.dp),
        ) {
            Text("GH", fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        OutlinedIconButton(
            onClick = { openUrl("https://twitter.com/") },
            modifier = Modifier.size(40.dp),
        ) {
            Text("X", fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        OutlinedIconButton(
            onClick = { openUrl("https://discord.gg/") },
            modifier = Modifier.size(40.dp),
        ) {
            Text("DC", fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
    }
}
