package com.example.minutemedicine.viewmodel

sealed class AppStateSaveMedicineBD {
    data class Loading(val progress:Int): AppStateSaveMedicineBD()
    data class Success(val message:String): AppStateSaveMedicineBD()
    data class Error(val error:String): AppStateSaveMedicineBD()

}