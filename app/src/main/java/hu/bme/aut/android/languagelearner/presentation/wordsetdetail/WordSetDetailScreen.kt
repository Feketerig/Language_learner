package hu.bme.aut.android.languagelearner.presentation.wordsetdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import hu.bme.aut.android.languagelearner.R
import hu.bme.aut.android.languagelearner.navigation.Screen
import hu.bme.aut.android.languagelearner.ui.theme.LanguageLearnerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSetDetailScreen(
    id: Int,
    navHostController: NavHostController,
    viewModel: WordSetDetailViewModel = hiltViewModel()
) {
    val words by viewModel.words

    Column() {
        /*FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            mainAxisSpacing = 8.dp,
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            //TODO optimize words filtering
            SuggestionChip(onClick = {}, label = { Text(text = "All: ${words.size}")}, shape = CircleShape)
            SuggestionChip(onClick = {}, label = { Text(text = "Memorized: ${words.filter(WordPair::memorized).size}")}, shape = CircleShape)
            SuggestionChip(onClick = {}, label = { Text(text = "Not memorized: ${words.filterNot(WordPair::memorized).size}")}, shape = CircleShape)
        }*/
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(start = 5.dp, end = 5.dp, bottom = 10.dp),
            modifier = Modifier.weight(1f)
        ){
            items(words.size){ index ->
                val word = words[index]
                WordListView(
                    id = word.id,
                    first = word.first,
                    second = word.second
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            mainAxisSpacing = 15.dp,
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            AssistChip(
                onClick = {navHostController.navigate(Screen.Learning.route + "/$id")},
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                label = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Practice")
                } },
                shape = CircleShape,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_local_library_24),
                        contentDescription = "Practice"
                    )
                },
                modifier = Modifier.fillMaxWidth(0.45f)
            )
            AssistChip(
                onClick = {navHostController.navigate(Screen.Quiz.route + "/$id")},
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                label = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Exercise")
                    } },
                shape = CircleShape,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_school_24),
                        contentDescription = "Exercise"
                    )
                },
                modifier = Modifier.fillMaxWidth(0.45f)
            )
        }
    }
}

@Composable
fun WordListView(
    id: Int,
    first: String,
    second: String
) {
    Card(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = first, style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = second, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Preview
@Composable
fun WordSetDetailPreview() {
    LanguageLearnerTheme {
        WordSetDetailScreen(1, rememberNavController())
    }
}