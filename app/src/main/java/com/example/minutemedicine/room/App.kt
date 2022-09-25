package com.example.minutemedicine.room

import android.app.Application
import androidx.room.Room
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.room.histirymedicaments.HistoryDatabaseMedicament
import com.example.minutemedicine.room.histirymedicaments.HistoryMedicamentDAO
import com.example.minutemedicine.room.historyhealth.HistoryDatabaseHealth
import com.example.minutemedicine.room.historyhealth.HistoryHealthDAO
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var dbMedicament: HistoryDatabaseMedicament? = null
        private var dbHealth: HistoryDatabaseHealth? = null
        var reminder:ReminderDTO? = null

        fun historyMedicamentDAO(): HistoryMedicamentDAO {
            if (dbMedicament == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException("Всё плохо")
                } else {
                    dbMedicament = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabaseMedicament::class.java, "Medicament.db"
                    )

                        .build()
                }
            }
            return dbMedicament!!.historyMedicamentDao()
        }

        fun historyHealthDAO(): HistoryHealthDAO {
            if (dbHealth == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException("Всё плохо")
                } else {
                    dbHealth = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabaseHealth::class.java, "Health.db"
                    )

                        .build()
                }
            }
            return dbHealth!!.historyHealthDao()
        }

    }
}