package hu.bme.aut.android.languagelearner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.bme.aut.android.languagelearner.presentation.login.LoginScreen
import hu.bme.aut.android.languagelearner.presentation.wordlearning.LearningScreen
import hu.bme.aut.android.languagelearner.presentation.wordquiz.QuizScreen
import hu.bme.aut.android.languagelearner.presentation.wordsetdetail.WordSetDetailScreen
import hu.bme.aut.android.languagelearner.presentation.wordsetlist.WordSetListScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ){
        composable(route = Screen.SetList.route){
            WordSetListScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.SetDetail.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getInt("id")?.let { it1 ->
                WordSetDetailScreen(it1, navHostController)
            }
        }
        composable(
            route = Screen.Learning.route + "/{setId}",
            arguments = listOf(
                navArgument("setId"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getInt("setId")?.let { setId ->
                LearningScreen(setId, navHostController)
            }
        }
        composable(
            route = Screen.Quiz.route + "/{setId}",
            arguments = listOf(
                navArgument("setId"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getInt("setId")?.let { setId ->
                QuizScreen(setId = setId, navHostController = navHostController)
            }
        }
        composable(
            route = Screen.Logout.route,
        ) {
            LoginScreen(
                mode = "logout",
                navController = navHostController,
                target = Screen.SetList.route
            )
        }
        composable(
            route = Screen.Login.route,
        ) {
            LoginScreen(
                mode = "login",
                navController = navHostController,
                target = Screen.SetList.route
            )
        }
    }

}