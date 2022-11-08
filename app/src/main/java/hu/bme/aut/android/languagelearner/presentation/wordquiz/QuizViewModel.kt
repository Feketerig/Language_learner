package hu.bme.aut.android.languagelearner.presentation.wordquiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.languagelearner.domain.model.WordPair
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel@Inject constructor(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var setId = savedStateHandle.get<Int>("setId") ?: 0

    var words = emptyList<WordPair>()

    var score by mutableStateOf(0)

    var actualWordIndex by mutableStateOf(0)

    var actualQuizType by mutableStateOf(QuizTypes.SHORT_ANSWER)

    var selectedQuiz by mutableStateOf(QuizTypes.values().toSet())

    private val errorText = "The correct answer is: "
    var error by mutableStateOf("")

    var answer by mutableStateOf("")

    var isChecked by mutableStateOf(false)

    var done by mutableStateOf(false)

    var multipleChoiceAnswers by mutableStateOf(emptyList<String>())

    fun getWords(setId: Int) {
        viewModelScope.launch {
            words = wordRepository.getWordsBySetId(id = setId)
            actualQuizType = selectedQuiz.random()
            generateAnswersForMultipleChoice()
        }
    }

    fun onTextChange(newValue: String){
        answer = newValue
    }

    fun validateAnswer() {
        isChecked = true
        when (actualQuizType) {
            QuizTypes.SHORT_ANSWER ->
                if (answer != words[actualWordIndex].second) {
                    error = errorText + words[actualWordIndex].second
                } else {
                    score++
                }
            QuizTypes.MULTIPLE_CHOICE ->
                if (answer == words[actualWordIndex].second) {
                    score++
                }
        }
    }

    fun generateNewQuiz(){
        actualWordIndex++
        if (actualWordIndex == words.size){
            done = true
            viewModelScope.launch {
                wordRepository.sendScore(courseId = setId, score = score)
            }
            return
        }
        actualQuizType = selectedQuiz.random()
        if(actualQuizType == QuizTypes.MULTIPLE_CHOICE){
            generateAnswersForMultipleChoice()
        }
        error = ""
        answer = ""
        isChecked = false
    }

    private fun generateAnswersForMultipleChoice() {
        val actualWordRemovedList = words.map { it.second }.toMutableList()
        actualWordRemovedList.remove(words[actualWordIndex].second)
        multipleChoiceAnswers = actualWordRemovedList.shuffled()
    }
}