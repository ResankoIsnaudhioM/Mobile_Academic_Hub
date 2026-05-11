package com.example.mobileacademichub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileacademichub.data.model.Assignment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssignmentViewModel : ViewModel() {

    private val _assignments = MutableStateFlow<List<Assignment>>(emptyList())
    val assignments: StateFlow<List<Assignment>> = _assignments

    init {
        fetchAssignments()
    }

    private fun fetchAssignments() {
        viewModelScope.launch {
            // Simulate fetching data from a repository or API
            _assignments.value = listOf(
                Assignment("A1", "Laporan Akhir", "Pemrograman Mobile", "2026-06-15", "Buat laporan akhir proyek aplikasi mobile.", false),
                Assignment("A2", "Studi Kasus Basis Data", "Basis Data", "2026-06-10", "Analisis studi kasus sistem basis data.", true),
                Assignment("A3", "Presentasi Kelompok", "Struktur Data", "2026-06-20", "Presentasi tentang algoritma sorting.", false)
            )
        }
    }

    fun toggleAssignmentCompletion(assignmentId: String) {
        _assignments.value = _assignments.value.map { assignment ->
            if (assignment.id == assignmentId) {
                assignment.copy(isCompleted = !assignment.isCompleted)
            } else {
                assignment
            }
        }
    }
}
