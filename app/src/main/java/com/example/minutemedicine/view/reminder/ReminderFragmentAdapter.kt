package com.example.minutemedicine.view.reminder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentReminderRecyclerItemAllBinding
import com.example.minutemedicine.model.ReminderDTO

class ReminderFragmentAdapter :
    RecyclerView.Adapter<ReminderFragmentAdapter.AddReminderFragmentHolder>() {

    private var reminderData: List<ReminderDTO> = listOf()

    fun setReminder(data: List<ReminderDTO>) {
        this.reminderData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddReminderFragmentHolder {
        return AddReminderFragmentHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_reminder_recycler_item_all, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddReminderFragmentHolder, position: Int) {
        holder.bind(this.reminderData[position])
    }

    override fun getItemCount(): Int {
        return reminderData.size
    }

    inner class AddReminderFragmentHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(reminderDTO: ReminderDTO) {
            FragmentReminderRecyclerItemAllBinding.bind(itemView).run {
                nameMedicament.text = reminderDTO.nameMedicament
                details.text = reminderDTO.details
                time.text = timeConvert(reminderDTO.time)
            }
        }

        private fun timeConvert(time: List<String>): String {
            var result = ""
            time.forEach {
                result += "$it\n"
            }
            return result
        }
    }


}