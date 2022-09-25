package com.example.minutemedicine.room.historyhealth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_health_entity")
data class HistoryHealthEntity(
    @PrimaryKey var nameHealth: String = "",
    var textHealth: String = ""
)