package com.example.minutemedicine.repository

import android.util.Log
import com.example.minutemedicine.model.HealthDTO
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.room.App
import com.example.minutemedicine.room.histirymedicaments.HistoryMedicamentEntity
import com.example.minutemedicine.room.historyhealth.HistoryHealthEntity

class RepositoryHistoryImpl : RepositoryHistoryMedicaments, RepositoryHistoryHealth {
    override fun saveMedicaments(reminder: ReminderDTO) {
        Thread {
            App.historyMedicamentDAO().insert(convertMedicament(reminder))
        }.start()
    }

    override fun updateMedicaments(reminder: ReminderDTO) {
        Thread {
            val g = App.historyMedicamentDAO().update(convertMedicament(reminder))
            Log.d("mylog","$g")
        }.start()
    }

    override fun deleteMedicaments(reminder: ReminderDTO) {
        Thread {
            App.historyMedicamentDAO().delete(convertMedicament(reminder))
        }.start()
    }

    override fun getAllHistoryMedicaments(): List<ReminderDTO> {
        return convertMedicament(App.historyMedicamentDAO().getAllHistoryMedicament())
    }

    private fun convertMedicament(reminder: ReminderDTO): HistoryMedicamentEntity {
        return HistoryMedicamentEntity(
            reminder.nameMedicament,
            reminder.howApply,
            reminder.applicationDays,
            reminder.switch,
            reminder.time,
            reminder.details
        )
    }

    private fun convertMedicament(history: List<HistoryMedicamentEntity>): List<ReminderDTO> {
        return history.map {
            ReminderDTO(
                it.nameMedicament,
                it.howApply!!,
                it.applicationDays!!,
                it.switch!!,
                it.time!!,
                it.details!!
            )
        }
    }

    //------------------------------------------------------------------------------------------------------//
    //Health
    //------------------------------------------------------------------------------------------------------//
    override fun saveHealth(health: HealthDTO) {
        Thread {
            App.historyHealthDAO().insert(HistoryHealthEntity(health.nameHealth, health.textHealth))
        }.start()
    }

    override fun updateHealth(health: HealthDTO) {
        Thread {
            App.historyHealthDAO().update(HistoryHealthEntity(health.nameHealth, health.textHealth))
        }.start()
    }

    override fun deleteHealth(health: HealthDTO) {
        Thread {
            App.historyHealthDAO().delete(HistoryHealthEntity(health.nameHealth, health.textHealth))
        }.start()
    }

    override fun getAllHistoryHealth(): List<HealthDTO> {
        return App.historyHealthDAO().getAllHistoryHealth().map {
            HealthDTO(it.nameHealth, it.textHealth)
        }
    }
}