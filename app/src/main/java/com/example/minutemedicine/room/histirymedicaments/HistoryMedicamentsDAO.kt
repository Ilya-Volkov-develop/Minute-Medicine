package com.example.minutemedicine.room.histirymedicaments

import androidx.room.*

@Dao
interface HistoryMedicamentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryMedicamentEntity)

    @Delete
    fun delete(entity: HistoryMedicamentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: HistoryMedicamentEntity):Int

    @Query("select * FROM history_medicament_entity")
    fun getAllHistoryMedicament(): List<HistoryMedicamentEntity>

}