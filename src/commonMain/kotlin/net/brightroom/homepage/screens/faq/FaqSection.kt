package net.brightroom.homepage.screens.faq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bright_room.generated.resources.Res
import bright_room.generated.resources.faq_a1
import bright_room.generated.resources.faq_a2
import bright_room.generated.resources.faq_a3
import bright_room.generated.resources.faq_a4
import bright_room.generated.resources.faq_a5
import bright_room.generated.resources.faq_desc
import bright_room.generated.resources.faq_label
import bright_room.generated.resources.faq_q1
import bright_room.generated.resources.faq_q2
import bright_room.generated.resources.faq_q3
import bright_room.generated.resources.faq_q4
import bright_room.generated.resources.faq_q5
import bright_room.generated.resources.faq_title
import net.brightroom.homepage.components.SectionHeader
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FaqSection(modifier: Modifier = Modifier) {
    val faqItems =
        listOf(
            Res.string.faq_q1 to Res.string.faq_a1,
            Res.string.faq_q2 to Res.string.faq_a2,
            Res.string.faq_q3 to Res.string.faq_a3,
            Res.string.faq_q4 to Res.string.faq_a4,
            Res.string.faq_q5 to Res.string.faq_a5,
        )

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
                label = stringResource(Res.string.faq_label),
                title = stringResource(Res.string.faq_title),
                description = stringResource(Res.string.faq_desc),
            )

            Spacer(Modifier.height(48.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                faqItems.forEach { (qRes, aRes) ->
                    FaqItem(
                        question = stringResource(qRes),
                        answer = stringResource(aRes),
                    )
                }
            }
        }
    }
}

@Composable
private fun FaqItem(
    question: String,
    answer: String,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = question,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier.rotate(if (expanded) 180f else 0f),
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Text(
                    text = answer,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 26.sp,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
                )
            }
        }
    }
}
