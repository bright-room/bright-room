package net.brightroom.homepage.screens.contributing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContributingSection(modifier: Modifier = Modifier) {
    val steps = listOf(
        Triple("01", Res.string.contrib_step1_title, Res.string.contrib_step1_desc),
        Triple("02", Res.string.contrib_step2_title, Res.string.contrib_step2_desc),
        Triple("03", Res.string.contrib_step3_title, Res.string.contrib_step3_desc),
        Triple("04", Res.string.contrib_step4_title, Res.string.contrib_step4_desc),
    )

    val rules = listOf(
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
        modifier = Modifier
            .widthIn(max = 1200.dp)
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

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            maxItemsInEachRow = 4,
        ) {
            steps.forEach { (num, titleRes, descRes) ->
                ContribStepCard(
                    number = num,
                    title = stringResource(titleRes),
                    description = stringResource(descRes),
                    modifier = Modifier.weight(1f).widthIn(min = 220.dp, max = 300.dp),
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Rules card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            border = CardDefaults.outlinedCardBorder(),
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
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Box(modifier = Modifier.padding(32.dp)) {
            Text(
                text = number,
                fontFamily = FontFamily.Monospace,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            )
            Column(modifier = Modifier.padding(top = 48.dp)) {
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
