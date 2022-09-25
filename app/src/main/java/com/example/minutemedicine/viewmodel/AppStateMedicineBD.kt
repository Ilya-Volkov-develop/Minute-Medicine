package com.example.minutemedicine.viewmodel

import com.example.minutemedicine.model.ReminderDTO

sealed class AppStateMedicineBD {
    data class Loading(val progress:Int): AppStateMedicineBD()
    data class Success(var medicineInfoHistoryData:List<ReminderDTO>,val delete:Boolean): AppStateMedicineBD()
    data class Error(val error:String): AppStateMedicineBD()

}