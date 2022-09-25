package com.example.minutemedicine.view.clock

import com.example.minutemedicine.model.ReminderDTO


interface OnItemClickListener {
    fun onItemClick(reminderDTO: ReminderDTO, tools:String)
}