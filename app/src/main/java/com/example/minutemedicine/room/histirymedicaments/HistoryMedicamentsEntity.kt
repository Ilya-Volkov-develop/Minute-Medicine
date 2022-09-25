package com.example.minutemedicine.room.histirymedicaments

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "history_medicament_entity")
@TypeConverters(ListToString::class)
data class HistoryMedicamentEntity(
    @PrimaryKey var nameMedicament: String = "",
    var howApply: String? = "",
    var applicationDays: List<String>? = listOf(),
    var switch: Boolean? = true,
    var time: List<String>? = listOf(),
    var details: String? = ""
)

class ListToString() {
    @TypeConverter
    fun toString(images: List<String>?): String? {
        if (images == null) return null

        val stringList = mutableListOf<String>()
        images.forEach {
            stringList.add(it)
        }

        return stringList.joinToString(",")
    }

    @TypeConverter
    fun toStringList(str: String?): List<String>? {
        if (str == null) return listOf()
        if (str == "") return listOf()

        return str.split(",")
    }
}
