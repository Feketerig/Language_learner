package hu.bme.aut.android.languagelearner.presentation.wordsetlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import hu.bme.aut.android.languagelearner.domain.model.WordTag
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSet(
    title: String,
    description: String,
    deadline: Instant,
    wordsCount: Int,
    wordsCountMemorized: Int,
    tags: List<WordTag>,
    onclick: () -> Unit,
    onTagClick: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onclick
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$wordsCountMemorized/$wordsCount cards memorized", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Deadline: ${deadline.toLocalDateTime(TimeZone.currentSystemDefault()).date}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.End))
            FlowRow(modifier = Modifier.fillMaxWidth(), mainAxisSpacing = 8.dp, mainAxisSize = SizeMode.Wrap) {
                tags.forEach { tag ->
                    SuggestionChip(
                        onClick = { onTagClick(tag.id) },
                        label = { Text(text = tag.tag) },
                        shape = CircleShape
                    )
                }
            }
        }
    }
}