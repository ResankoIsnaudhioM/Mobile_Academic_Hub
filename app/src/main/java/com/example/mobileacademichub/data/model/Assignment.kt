package com.example.mobileacademichub.data.model

data class Assignment(
    val id: String,
    val title: String,
    val course: String,
    val dueDate: String,
    val description: String,
    val isCompleted: Boolean = false
)
