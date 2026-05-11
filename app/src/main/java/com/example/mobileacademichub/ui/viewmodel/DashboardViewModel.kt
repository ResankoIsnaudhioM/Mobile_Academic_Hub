package com.example.mobileacademichub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileacademichub.data.model.Assignment
import com.example.mobileacademichub.data.model.ScheduleItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DashboardViewModel(private val scheduleViewModel: ScheduleViewModel = ScheduleViewModel(), private val assignmentViewModel: AssignmentViewModel = AssignmentViewModel()) : ViewModel() {

    private val _upcomingEvents = MutableStateFlow<List<Any>>(emptyList())
    val upcomingEvents: StateFlow<List<Any>> = _upcomingEvents

    init {
        viewModelScope.launch {
            combine(scheduleViewModel.scheduleItems, assignmentViewModel.assignments) { schedules, assignments ->
                val events = mutableListOf<Any>()
                events.addAll(schedules)
                events.addAll(assignments.filter { !it.isCompleted })
                events.sortedBy { 
                    when (it) {
                        is ScheduleItem -> it.startTime // Simplified sorting for demo
                        is Assignment -> it.dueDate // Simplified sorting for demo
                        else -> ""
                    }
                }
            }.collect { combinedList ->
                _upcomingEvents.value = combinedList
            }
        }
    }
}
