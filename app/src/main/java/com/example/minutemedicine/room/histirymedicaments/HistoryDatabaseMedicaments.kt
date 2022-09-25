package com.example.minutemedicine.room.histirymedicaments

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryMedicamentEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabaseMedicament : RoomDatabase() {
    abstract fun historyMedicamentDao(): HistoryMedicamentDAO
}