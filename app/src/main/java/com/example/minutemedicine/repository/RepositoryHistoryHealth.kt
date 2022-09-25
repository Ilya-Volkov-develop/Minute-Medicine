package com.example.minutemedicine.repository

import com.example.minutemedicine.model.HealthDTO

interface RepositoryHistoryHealth {
    fun saveHealth(health: HealthDTO)
    fun updateHealth(health: HealthDTO)
    fun deleteHealth(health: HealthDTO)
    fun getAllHistoryHealth(): List<HealthDTO>
}