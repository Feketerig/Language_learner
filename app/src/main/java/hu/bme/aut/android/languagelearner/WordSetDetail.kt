package hu.bme.aut.android.languagelearner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import hu.bme.aut.android.languagelearner.ui.theme.LanguageLearnerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSetDetail() {
    Column() {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            mainAxisSpacing = 8.dp,
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            SuggestionChip(onClick = {}, label = { Text(text = "all")}, shape = CircleShape)
            SuggestionChip(onClick = {}, label = { Text(text = "memorized")}, shape = CircleShape)
            SuggestionChip(onClick = {}, label = { Text(text = "not memorized")}, shape = CircleShape)
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(start = 5.dp, end = 5.dp, bottom = 10.dp),
            modifier = Modifier.weight(1f)
        ){
            items(30){
                WordListView()
            }
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            mainAxisSpacing = 15.dp,
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            AssistChip(
                onClick = {},
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                label = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Practice")
                } },
                shape = CircleShape,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_local_library_24),
                        contentDescription = "Practice"
                    )
                },
                modifier = Modifier.fillMaxWidth(0.45f)
            )
            AssistChip(
                onClick = {},
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                label = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Exercise")
                    } },
                shape = CircleShape,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_school_24),
                        contentDescription = "Exercise"
                    )
                },
                modifier = Modifier.fillMaxWidth(0.45f)
            )
        }
    }
}

@Composable
fun WordListView() {
    Card(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = "Eleje", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Hátulja", style = MaterialTheme.typography.labelSmall)
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun WordSetDetailPreview() {
    LanguageLearnerTheme {
        WordSetDetail()
    }
}