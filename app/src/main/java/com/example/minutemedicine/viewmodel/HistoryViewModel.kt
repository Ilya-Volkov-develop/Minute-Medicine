package com.example.minutemedicine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minutemedicine.model.HealthDTO
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.repository.RepositoryHistoryImpl


class HistoryViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()): ViewModel() {

    private val repositoryHistoryImpl:RepositoryHistoryImpl by lazy {
        RepositoryHistoryImpl()
    }

    fun getLiveData() = liveData

    fun getAllMedicineHistory(){
        Thread{
            val listReminders = repositoryHistoryImpl.getAllHistoryMedicaments()
            liveData.postValue(AppStateMedicineBD.Success(listReminders,false))
        }.start()
    }

    fun deleteMedicine(reminder:ReminderDTO){
        Thread{
            repositoryHistoryImpl.deleteMedicaments(reminder)
            val listReminders = repositoryHistoryImpl.getAllHistoryMedicaments()
            liveData.postValue(AppStateMedicineBD.Success(listReminders,true))
        }.start()
    }

    fun saveMedicine(reminder: ReminderDTO){
        repositoryHistoryImpl.saveMedicaments(reminder)
    }

    fun saveHealth(health: HealthDTO){
        repositoryHistoryImpl.saveHealth(health)
    }

    fun getAllHealthHistory(){
        Thread{
            val listHealth = repositoryHistoryImpl.getAllHistoryHealth()
            liveData.postValue(AppStateHealthBD.Success(listHealth))
        }.start()
    }


}