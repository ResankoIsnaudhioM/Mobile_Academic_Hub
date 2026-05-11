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
import com.example.mobileacademichub.ui.viewmodel.ScheduleViewModel

@Composable
fun ScheduleScreen(modifier: Modifier = Modifier, scheduleViewModel: ScheduleViewModel = viewModel()) {
    val scheduleItems by scheduleViewModel.scheduleItems.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Jadwal Kuliah",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(scheduleItems) {
                scheduleItem ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = scheduleItem.courseName, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Dosen: ${scheduleItem.lecturer}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Waktu: ${scheduleItem.startTime} - ${scheduleItem.endTime}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Ruangan: ${scheduleItem.room}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Hari: ${scheduleItem.day}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
