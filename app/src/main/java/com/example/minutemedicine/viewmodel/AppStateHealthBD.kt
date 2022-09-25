package com.example.minutemedicine.viewmodel

import com.example.minutemedicine.model.HealthDTO

sealed class AppStateHealthBD {
    data class Loading(val progress:Int): AppStateHealthBD()
    data class Success(var healthInfoHistoryData:List<HealthDTO>): AppStateHealthBD()
    data class Error(val error:String): AppStateHealthBD()

}