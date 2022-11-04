package hu.bme.aut.android.languagelearner.presentation.wordsetlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import hu.bme.aut.android.languagelearner.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSetListScreen(
    navHostController: NavHostController,
    viewModel: WordSetListViewModel = hiltViewModel()
) {
    val wordSets by viewModel.wordSets.collectAsState(initial = emptyList())
    val tags by viewModel.allTags.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = viewModel.searchQuery,
                onValueChange = {
                    viewModel.onSearchTextChange(it)
                },
                placeholder = {
                    Text(text = "Keresés...")
                },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
        LazyRow(modifier = Modifier.padding(start = 5.dp)) {
            items(tags) { tag ->
                FilterChip(
                    selected = viewModel.selectedTags.contains(tag.id),
                    onClick = {
                              viewModel.onSelectedTagsChange(tag.id)
                    },
                    label = { Text(text = tag.tag) },
                    modifier = Modifier.padding(5.dp),
                    shape = CircleShape
                )
            }
        }
        LazyColumn() {
            items(wordSets.size) { index ->
                val wordSet = wordSets[index]
                WordSet(
                    title = wordSet.title,
                    description = wordSet.description,
                    wordsCount = wordSet.words.size,
                    wordsCountMemorized = wordSet.words.filter { it.memorized }.size,
                    tags = wordSet.tags,
                    onclick = {
                        navHostController.navigate(Screen.SetDetail.route + "/" + wordSet.id)
                    },
                    onTagClick = { id ->
                        viewModel.onSelectedTagsChange(id)
                    }
                )
            }
        }
    }
}

