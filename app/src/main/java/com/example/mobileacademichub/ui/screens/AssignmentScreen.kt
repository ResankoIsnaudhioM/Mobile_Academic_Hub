package com.example.mobileacademichub.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileacademichub.ui.viewmodel.AssignmentViewModel

@Composable
fun AssignmentScreen(modifier: Modifier = Modifier, assignmentViewModel: AssignmentViewModel = viewModel()) {
    val assignments by assignmentViewModel.assignments.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Daftar Tugas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(assignments) {
                assignment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = assignment.isCompleted,
                                onCheckedChange = { assignmentViewModel.toggleAssignmentCompletion(assignment.id) }
                            )
                            Column {
                                Text(text = assignment.title, style = MaterialTheme.typography.titleLarge)
                                Text(text = "Mata Kuliah: ${assignment.course}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Deadline: ${assignment.dueDate}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                        Text(text = assignment.description, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}
