package hu.bme.aut.android.languagelearner.presentation.wordsetlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.languagelearner.domain.model.WordSet
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSetListViewModel @Inject constructor(
    private val wordRepository: WordRepository
): ViewModel() {
    var selectedTags by mutableStateOf(setOf<Int>())

    var wordSets by mutableStateOf(emptyList<WordSet>())

    var allTags = wordRepository.getTags()

    var searchQuery by mutableStateOf("")



    init {
        viewModelScope.launch {
            wordRepository.sync()
            getWordSets()
        }
    }

    private fun getWordSets(
        searchQuery: String = "",
        tags: List<Int> = emptyList()
    ){
        viewModelScope.launch {
            wordRepository.getWordSets(searchQuery, tags).collect { result ->
                wordSets = result
            }
        }
    }

    fun onSearchTextChange(newValue: String){
        searchQuery = newValue
        getWordSets(searchQuery, selectedTags.toList())
    }

    fun onSelectedTagsChange(id: Int){
        selectedTags = if (selectedTags.contains(id)) {
            selectedTags - id
        } else {
            selectedTags + id
        }
        getWordSets(searchQuery, selectedTags.toList())
    }
}