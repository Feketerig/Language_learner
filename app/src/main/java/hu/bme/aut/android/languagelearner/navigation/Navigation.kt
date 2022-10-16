package hu.bme.aut.android.languagelearner.navigation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.languagelearner.ViewModel1
import hu.bme.aut.android.languagelearner.WordSet

@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = "1",
        modifier = modifier
    ){
        composable(route = "1"){
            valami()

            //WordSet("Title","Description - optional", 7, 0, listOf("angol", "magyar"))
        }
    }

}

@Composable
fun valami(
    viewModel1: ViewModel1 = hiltViewModel()
) {
    val state by viewModel1.state.collectAsState(initial = emptyList())
    LazyColumn(){
        items(state.size){ index ->
            val wordSet = state[index]
            WordSet(
                title = wordSet.title,
                description = wordSet.description,
                wordsCount = wordSet.words.size,
                wordsCountMemorized = wordSet.words.filter { it.memorized }.size,
                tags = wordSet.tags
            )
        }
    }
}