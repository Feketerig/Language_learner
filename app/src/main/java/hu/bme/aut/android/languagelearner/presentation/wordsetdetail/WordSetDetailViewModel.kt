package hu.bme.aut.android.languagelearner.presentation.wordsetdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.languagelearner.domain.model.WordPair
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSetDetailViewModel @Inject constructor(
    wordRepository: WordRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var words: MutableState<List<WordPair>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            words = mutableStateOf(wordRepository.getWordsBySetId(id))
        }
    }
}