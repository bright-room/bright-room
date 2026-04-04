package net.brightroom.homepage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.brightroom.homepage.app.LocalAppViewModel

@Composable
fun ThemeLanguageControls(
    modifier: Modifier = Modifier,
    buttonSize: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val viewModel = LocalAppViewModel.current

    val buttonColors =
        if (containerColor != Color.Unspecified) {
            IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            )
        } else {
            IconButtonDefaults.iconButtonColors(
                contentColor = contentColor,
            )
        }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { viewModel.toggleTheme() },
            modifier = Modifier.size(buttonSize),
            colors = buttonColors,
        ) {
            Icon(
                imageVector = if (viewModel.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Toggle theme",
                modifier = Modifier.size(iconSize),
            )
        }

        IconButton(
            onClick = { viewModel.toggleLanguage() },
            modifier = Modifier.size(buttonSize),
            colors = buttonColors,
        ) {
            Text(
                text = if (viewModel.isJapanese) "EN" else "JA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
            )
        }
    }
}
