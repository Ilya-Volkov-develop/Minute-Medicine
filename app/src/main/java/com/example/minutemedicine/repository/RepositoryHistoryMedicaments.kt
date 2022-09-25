package com.example.minutemedicine.repository

import com.example.minutemedicine.model.ReminderDTO

interface RepositoryHistoryMedicaments {
    fun saveMedicaments(reminder: ReminderDTO)
    fun updateMedicaments(reminder: ReminderDTO)
    fun deleteMedicaments(reminder: ReminderDTO)
    fun getAllHistoryMedicaments(): List<ReminderDTO>
}