package hu.bme.aut.android.languagelearner.presentation.wordsetlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.domain.model.WordSet
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSetListViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val wordApi: WordApi
): ViewModel() {
    //TODO check if this is good
    var selectedTags by mutableStateOf(setOf<Int>())

    var wordSets = getWordSets("", setOf())

    var allTags = wordRepository.getTags()

    var searchQuery by mutableStateOf("")



    init {
        viewModelScope.launch {
            wordRepository.sync()
        }
    }

    private fun getWordSets(
        searchQuery: String,
        tags: Set<Int>
    ): Flow<List<WordSet>> = wordRepository.getWordSets(searchQuery, tags).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchTextChange(newValue: String){
        searchQuery = newValue
        updateWords()
    }

    fun onSelectedTagsChange(id: Int){
        selectedTags = if (selectedTags.contains(id)) {
            selectedTags - id
        } else {
            selectedTags + id
        }
        updateWords()
    }

    private fun updateWords(){
        wordSets = getWordSets(searchQuery, selectedTags)
    }
}