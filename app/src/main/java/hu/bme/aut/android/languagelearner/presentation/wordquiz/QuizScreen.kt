package hu.bme.aut.android.languagelearner.presentation.wordquiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    setId: Int,
    navHostController: NavHostController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = "Pick quiz type")
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pick what type of quiz do you want to play?")
                    Spacer(modifier = Modifier.height(10.dp))
                    QuizTypes.values().forEach { quizType ->
                        FilterChip(
                            selected = viewModel.selectedQuiz.contains(quizType),
                            onClick = {
                                viewModel.selectedQuiz = if (viewModel.selectedQuiz.contains(quizType)) {
                                    viewModel.selectedQuiz - quizType
                                } else {
                                    viewModel.selectedQuiz + quizType
                                }
                            },
                            label = { Text(text = quizType.text) },
                            shape = CircleShape
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog = false
                        viewModel.getWords(setId)
                    }) {
                    Text("Start")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog = false
                        navHostController.popBackStack()
                    }) {
                    Text("Cancel")
                }
            }
        )
    } else if (viewModel.done) {
        AlertDialog(
            onDismissRequest = {
                navHostController.popBackStack()
            },
            title = {
                Text(text = "Score")
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Your score is ${viewModel.score}/${viewModel.words.size}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        navHostController.popBackStack()
                    }) {
                    Text("OK")
                }
            }
        )
    }else if(viewModel.words.isNotEmpty()) {
        when(viewModel.actualQuizType){
            QuizTypes.SHORT_ANSWER -> ShortAnswerUI(viewModel)
            QuizTypes.MULTIPLE_CHOICE -> MultipleChoiceUI(2, viewModel)
            else -> Text(text = "No UI was found")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortAnswerUI(
    viewModel: QuizViewModel
) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Please write down the right answer!",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = viewModel.words[viewModel.actualWordIndex].first,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModel.answer,
            enabled = !viewModel.isChecked,
            isError = viewModel.error.isNotEmpty(),
            onValueChange = { viewModel.onTextChange(it) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (viewModel.error.isNotEmpty()) {
            Text(
                text = viewModel.error,
                color = MaterialTheme.colorScheme.error,
            )
        }else if (viewModel.isChecked){
            Text(
                text = "Your answer is right",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                if (viewModel.isChecked){
                    viewModel.generateNewQuiz()
                }else {
                    viewModel.validateAnswer()
                }
        }) {
            Text(text = if (viewModel.isChecked) "Next" else "Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleChoiceUI(
    numberOfChoices: Int,
    viewModel: QuizViewModel
) {
    val rightAnswerIndex by remember {
        mutableStateOf(Random.nextInt(numberOfChoices))
    }
    val answers by remember {
        val answers = viewModel.multipleChoiceAnswers.take(numberOfChoices-1).toMutableList()
        answers.add(rightAnswerIndex, viewModel.words[viewModel.actualWordIndex].second)
        mutableStateOf(answers.toList())
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Please select the right answer!",
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = viewModel.words[viewModel.actualWordIndex].first,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(5.dp))
        (0 until numberOfChoices).forEach { index ->
            val answer = answers[index]
            FilterChip(
                modifier = Modifier.fillMaxWidth(),
                selected = viewModel.answer == answer,
                enabled = !viewModel.isChecked,
                colors = FilterChipDefaults.filterChipColors(
                    disabledContainerColor = if (answer == viewModel.words[viewModel.actualWordIndex].second) {
                        Color.Green
                    }else {
                        Color.Transparent
                    },
                    disabledSelectedContainerColor = if (answer == viewModel.words[viewModel.actualWordIndex].second){
                        Color.Green
                    }else{
                        Color.Red
                    }
                ),
                onClick = {
                    viewModel.answer = answer
                    viewModel.validateAnswer()
                },
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = answer,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (viewModel.isChecked) {
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                        viewModel.generateNewQuiz()
                }) {
                Text(text = "Next" )
            }
        }
    }
}