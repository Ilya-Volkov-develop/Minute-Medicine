package com.example.minutemedicine.view.clock

import com.example.minutemedicine.model.ReminderDTO

interface OnItemClickListener {
    fun onItemClickGetSize(size:Int)
    fun onItemClickWorker(reminder: ReminderDTO, isChecked: Boolean)
}