package net.brightroom.homepage.screens.members

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.members_desc
import bright_room.generated.resources.members_label
import bright_room.generated.resources.members_title
import bright_room.generated.resources.role_backend
import bright_room.generated.resources.role_designer
import bright_room.generated.resources.role_devops
import bright_room.generated.resources.role_founder
import bright_room.generated.resources.role_frontend
import bright_room.generated.resources.role_mobile
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.data.MemberData
import net.brightroom.homepage.shared.lib.openUrl
import net.brightroom.homepage.shared.theme.AccentBlue
import org.jetbrains.compose.resources.stringResource

@Composable
private fun resolveRoleLabel(roleKey: String): String =
    when (roleKey) {
        "role_founder" -> stringResource(Res.string.role_founder)
        "role_frontend" -> stringResource(Res.string.role_frontend)
        "role_backend" -> stringResource(Res.string.role_backend)
        "role_designer" -> stringResource(Res.string.role_designer)
        "role_devops" -> stringResource(Res.string.role_devops)
        "role_mobile" -> stringResource(Res.string.role_mobile)
        else -> roleKey
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MembersSection(modifier: Modifier = Modifier) {
    val viewModel = LocalAppViewModel.current
    val members by viewModel.members.collectAsState()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 1200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp, bottom = 60.dp),
        ) {
            SectionHeader(
                label = stringResource(Res.string.members_label),
                title = stringResource(Res.string.members_title),
                description = stringResource(Res.string.members_desc),
            )

            Spacer(Modifier.height(48.dp))

            val density = LocalDensity.current
            var maxCardHeight by remember { mutableStateOf(0.dp) }

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val columns =
                    when {
                        maxWidth < 600.dp -> 2
                        maxWidth < 900.dp -> 3
                        else -> 4
                    }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    maxItemsInEachRow = columns,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    members.forEach { member ->
                        MemberCard(
                            member = member,
                            maxCardHeight = maxCardHeight,
                            onHeightMeasured = { h ->
                                val hDp = with(density) { h.toDp() }
                                if (hDp > maxCardHeight) maxCardHeight = hDp
                            },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    val remainder = members.size % columns
                    if (remainder != 0) {
                        repeat(columns - remainder) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberCard(
    member: MemberData,
    maxCardHeight: androidx.compose.ui.unit.Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .defaultMinSize(minHeight = maxCardHeight)
                .onSizeChanged { onHeightMeasured(it.height) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    AccentBlue.copy(alpha = 0.15f),
                                ),
                            ),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = member.avatarInitials,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = member.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = resolveRoleLabel(member.roleKey),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { openUrl(member.githubUrl) }) {
                Text(
                    text = "GitHub",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
