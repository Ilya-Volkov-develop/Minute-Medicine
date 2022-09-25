package com.example.minutemedicine.room.historyhealth

import androidx.room.*

@Dao
interface HistoryHealthDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryHealthEntity)

    @Delete
    fun delete(entity: HistoryHealthEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(entity: HistoryHealthEntity)

    @Query("select * FROM history_health_entity")
    fun getAllHistoryHealth(): List<HistoryHealthEntity>

}