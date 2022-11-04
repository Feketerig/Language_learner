package hu.bme.aut.android.languagelearner.presentation.wordlearning

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import hu.bme.aut.android.languagelearner.domain.model.WordPair
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSwipeableCardApi::class)
fun LearningScreen(
    setId: Int,
    navHostController: NavHostController,
    viewModel: LearningViewModel = hiltViewModel()
) {
    val words by viewModel.words

    var popBackStack by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .systemBarsPadding()
    ) {
        if (words.isNotEmpty()) {
            Box {
                val states = words.reversed()
                    .map { it to rememberSwipeableCardState() }
                val scope = rememberCoroutineScope()
                Box(
                    Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                ) {
                    states.forEach { (wordPair, state) ->
                        if (state.swipedDirection == null) {
                            ProfileCard(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .swipableCard(
                                        state = state,
                                        blockedDirections = listOf(Direction.Down),
                                        onSwiped = {

                                        },
                                        onSwipeCancel = {

                                        }
                                    ),
                                wordPair = wordPair
                            )
                        }
                        LaunchedEffect(wordPair, state.swipedDirection) {
                            if (state.swipedDirection != null) {
                                if (state.swipedDirection == Direction.Right) {
                                    viewModel.memorizedChanged(wordPair.id, true)
                                } else if (state.swipedDirection == Direction.Left) {
                                    viewModel.memorizedChanged(wordPair.id, false)
                                }
                            }
                        }
                        LaunchedEffect(states.first().second.swipedDirection){
                            if (states.first().second.swipedDirection != null && !popBackStack){
                                popBackStack = true
                                navHostController.popBackStack()
                            }
                        }
                    }
                }
                Row(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AssistChip(
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch {
                                val last = states.reversed()
                                    .firstOrNull {
                                        it.second.offset.value == Offset(0f, 0f)
                                    }?.second
                                last?.swipe(Direction.Left)
                            }
                        },
                        label = { Text(text = "Not memorized") }
                    )
                    AssistChip(
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch {
                                val last = states.reversed()
                                    .firstOrNull {
                                        it.second.offset.value == Offset(0f, 0f)
                                    }?.second

                                last?.swipe(Direction.Right)
                            }
                        },
                        label = { Text(text = "Memorized") }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileCard(
    modifier: Modifier,
    wordPair: WordPair
) {
    var isFlipped by remember {
        mutableStateOf(false)
    }

    val rotation by animateFloatAsState(
        targetValue = if (!isFlipped) 180f else 0f,
        animationSpec = tween(500)
    )
    val animateFront by animateFloatAsState(
        targetValue = if (isFlipped) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (!isFlipped) 1f else 0f,
        animationSpec = tween(500)
    )
    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            },
        onClick = {
            isFlipped = !isFlipped
        },
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.background)
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = if (isFlipped) wordPair.second else wordPair.first,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        alpha = if (!isFlipped) animateBack else animateFront
                        rotationY = rotation
                    }
            )
        }
    }
}