package com.example.minutemedicine.room.historyhealth

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryHealthEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabaseHealth : RoomDatabase() {
    abstract fun historyHealthDao(): HistoryHealthDAO
}