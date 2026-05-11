package com.example.mobileacademichub.data.model

data class ScheduleItem(
    val id: String,
    val courseName: String,
    val lecturer: String,
    val startTime: String,
    val endTime: String,
    val room: String,
    val day: String
)
