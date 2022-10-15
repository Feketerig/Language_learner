package hu.bme.aut.android.languagelearner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            WordSet("Title","Description - optional", 7, 0, listOf("angol", "magyar"))
        }
    }

}