package com.example.minutemedicine.model

data class ReminderDTO(
    val nameMedicament: String,
    val howApply: String,
    val applicationDays: List<String>,
    val switch: Boolean,
    val time: List<String>,
    val details: String
)
