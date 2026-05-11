package com.example.mobileacademichub.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileacademichub.data.model.Assignment
import com.example.mobileacademichub.data.model.ScheduleItem
import com.example.mobileacademichub.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, dashboardViewModel: DashboardViewModel = viewModel()) {
    val upcomingEvents by dashboardViewModel.upcomingEvents.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(upcomingEvents) {
                event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        when (event) {
                            is ScheduleItem -> {
                                Text(text = "Jadwal: ${event.courseName}", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Dosen: ${event.lecturer}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Waktu: ${event.startTime} - ${event.endTime} (${event.day})", style = MaterialTheme.typography.bodyMedium)
                            }
                            is Assignment -> {
                                Text(text = "Tugas: ${event.title}", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Mata Kuliah: ${event.course}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Deadline: ${event.dueDate}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
