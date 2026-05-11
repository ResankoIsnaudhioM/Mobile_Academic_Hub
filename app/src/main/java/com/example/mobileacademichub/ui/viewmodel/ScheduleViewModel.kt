package com.example.mobileacademichub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileacademichub.data.model.ScheduleItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val _scheduleItems = MutableStateFlow<List<ScheduleItem>>(emptyList())
    val scheduleItems: StateFlow<List<ScheduleItem>> = _scheduleItems

    init {
        fetchScheduleItems()
    }

    private fun fetchScheduleItems() {
        viewModelScope.launch {
            // Simulate fetching data from a repository or API
            _scheduleItems.value = listOf(
                ScheduleItem("1", "Pemrograman Mobile", "Dr. Budi", "08:00", "10:00", "R. 301", "Senin"),
                ScheduleItem("2", "Basis Data", "Prof. Ani", "10:00", "12:00", "R. 302", "Senin"),
                ScheduleItem("3", "Struktur Data", "Dr. Cici", "13:00", "15:00", "R. 303", "Selasa"),
                ScheduleItem("4", "Jaringan Komputer", "Prof. Doni", "09:00", "11:00", "R. 304", "Rabu")
            )
        }
    }
}
