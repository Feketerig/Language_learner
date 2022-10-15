package hu.bme.aut.android.languagelearner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import hu.bme.aut.android.languagelearner.ui.theme.LanguageLearnerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSet(
    title: String,
    description: String,
    wordsCount: Int,
    wordsCountMemorized: Int,
    tags: List<String>
) {
    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$wordsCountMemorized/$wordsCount cards memorized", style = MaterialTheme.typography.labelSmall)
            FlowRow(modifier = Modifier.fillMaxWidth(), mainAxisSpacing = 8.dp, mainAxisSize = SizeMode.Wrap) {
                tags.forEach { text ->
                    SuggestionChip(onClick = {}, label = { Text(text = text)}, shape = CircleShape)
                }
            }
        }
    }
}

@Preview
@Composable
fun WordSetPreview() {
    LanguageLearnerTheme {
        WordSet("Title","Description - optional", 7, 0, listOf("angol", "magyar"))
    }
}