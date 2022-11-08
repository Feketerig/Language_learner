package hu.bme.aut.android.languagelearner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.languagelearner.navigation.Navigation
import hu.bme.aut.android.languagelearner.navigation.Screen
import hu.bme.aut.android.languagelearner.ui.theme.LanguageLearnerTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var showMenu by remember {
                mutableStateOf(false)
            }
            LanguageLearnerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        /*floatingActionButton = {
                            if (navHostController.currentBackStackEntryAsState().value?.destination?.route == Screen.SetList.route) {
                                FloatingActionButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add new word set",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        },*/
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "Language learner")
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                actions = {

                                    IconButton(onClick = { showMenu = !showMenu }) {
                                        Icon(
                                            imageVector = Icons.Filled.MoreVert,
                                            contentDescription = "Logout"
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showMenu,
                                        onDismissRequest = { showMenu = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text(text = "Logout") },
                                            onClick = {
                                                showMenu = false
                                                if (loggedIn) {
                                                    navHostController.navigate(Screen.Logout.route)
                                                }
                                            })
                                    }
                                }
                            )
                        },

                    ) { paddingValues ->
                        Navigation(
                            navHostController = navHostController,
                            modifier = Modifier
                                .padding(paddingValues)
                        )
                    }
                }

            }
        }
    }

    companion object{
        var loggedIn = false
    }
}