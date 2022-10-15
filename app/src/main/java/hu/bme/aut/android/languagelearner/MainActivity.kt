package hu.bme.aut.android.languagelearner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.languagelearner.navigation.Navigation
import hu.bme.aut.android.languagelearner.ui.theme.LanguageLearnerTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            LanguageLearnerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {  }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add new word set",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        },
                        topBar = {
                            CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Language learner")
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )}
                    ) { paddingValues ->
                        Navigation(
                            navHostController = navHostController,
                            modifier = Modifier
                                .padding(paddingValues)
                        )
                        /*LazyColumn(contentPadding = paddingValues){
                            items(20){
                                WordSet("Title","Description - optional", 7, 0, listOf("angol", "magyar"))
                            }
                        }*/
                    }
                }

            }
        }
    }
}