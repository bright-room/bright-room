package net.brightroom.homepage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bright_room.generated.resources.Res
import bright_room.generated.resources.github
import net.brightroom.homepage.app.LocalAppViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun primaryButtonContentColor(): Color {
    val viewModel = LocalAppViewModel.current
    return if (viewModel.isDarkTheme) {
        MaterialTheme.colorScheme.background
    } else {
        Color.White
    }
}

@Composable
fun GitHubButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = primaryButtonContentColor()

    Button(
        onClick = onClick,
        modifier = modifier.hoverFloat(shape = RoundedCornerShape(10.dp), highlight = HoverHighlight.GLOW),
        shape = RoundedCornerShape(10.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = contentColor,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 6.dp),
        ) {
            Image(
                painter = painterResource(Res.drawable.github),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                colorFilter = ColorFilter.tint(contentColor),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
