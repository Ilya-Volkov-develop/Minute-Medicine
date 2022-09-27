package com.example.minutemedicine.view.home

import com.example.minutemedicine.model.ReminderDTO

interface OnItemClickListener {
    fun onItemClick(reminder: ReminderDTO, isChecked: Boolean)
}