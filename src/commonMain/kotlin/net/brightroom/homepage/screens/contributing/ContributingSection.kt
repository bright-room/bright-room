package net.brightroom.homepage.screens.contributing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.contrib_desc
import bright_room.generated.resources.contrib_label
import bright_room.generated.resources.contrib_rule1
import bright_room.generated.resources.contrib_rule2
import bright_room.generated.resources.contrib_rule3
import bright_room.generated.resources.contrib_rule4
import bright_room.generated.resources.contrib_rule5
import bright_room.generated.resources.contrib_rules_title
import bright_room.generated.resources.contrib_step1_desc
import bright_room.generated.resources.contrib_step1_title
import bright_room.generated.resources.contrib_step2_desc
import bright_room.generated.resources.contrib_step2_title
import bright_room.generated.resources.contrib_step3_desc
import bright_room.generated.resources.contrib_step3_title
import bright_room.generated.resources.contrib_step4_desc
import bright_room.generated.resources.contrib_step4_title
import bright_room.generated.resources.contrib_title
import net.brightroom.homepage.components.SectionHeader
import net.brightroom.homepage.components.hoverFloat
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContributingSection(modifier: Modifier = Modifier) {
    data class ContribStep(
        val number: String,
        val icon: ImageVector,
        val titleRes: StringResource,
        val descRes: StringResource,
    )

    val steps =
        listOf(
            ContribStep("01", Icons.Default.ForkRight, Res.string.contrib_step1_title, Res.string.contrib_step1_desc),
            ContribStep("02", Icons.Default.AccountTree, Res.string.contrib_step2_title, Res.string.contrib_step2_desc),
            ContribStep("03", Icons.Default.Code, Res.string.contrib_step3_title, Res.string.contrib_step3_desc),
            ContribStep("04", Icons.AutoMirrored.Filled.Send, Res.string.contrib_step4_title, Res.string.contrib_step4_desc),
        )

    val rules =
        listOf(
            Res.string.contrib_rule1,
            Res.string.contrib_rule2,
            Res.string.contrib_rule3,
            Res.string.contrib_rule4,
            Res.string.contrib_rule5,
        )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp, bottom = 60.dp),
        ) {
            SectionHeader(
                label = stringResource(Res.string.contrib_label),
                title = stringResource(Res.string.contrib_title),
                description = stringResource(Res.string.contrib_desc),
            )

            Spacer(Modifier.height(48.dp))

            val density = LocalDensity.current
            var maxStepCardHeight by remember { mutableStateOf(0.dp) }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                maxItemsInEachRow = 4,
            ) {
                steps.forEach { step ->
                    ContribStepCard(
                        number = step.number,
                        icon = step.icon,
                        title = stringResource(step.titleRes),
                        description = stringResource(step.descRes),
                        maxCardHeight = maxStepCardHeight,
                        onHeightMeasured = { h ->
                            val hDp = with(density) { h.toDp() }
                            if (hDp > maxStepCardHeight) maxStepCardHeight = hDp
                        },
                        modifier = Modifier.weight(1f).widthIn(min = 220.dp),
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Rules card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        text = stringResource(Res.string.contrib_rules_title),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(16.dp))
                    rules.forEach { ruleRes ->
                        RuleItem(stringResource(ruleRes))
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ContribStepCard(
    number: String,
    icon: ImageVector,
    title: String,
    description: String,
    maxCardHeight: androidx.compose.ui.unit.Dp,
    onHeightMeasured: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .defaultMinSize(minHeight = maxCardHeight)
                .onSizeChanged { onHeightMeasured(it.height) }
                .hoverFloat(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = number,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                )
            }
            Spacer(Modifier.height(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                )
            }
        }
    }
}

@Composable
private fun RuleItem(text: String) {
    Row {
        Text(
            text = "→",
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 12.dp),
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp,
        )
    }
}
