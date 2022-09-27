package com.example.minutemedicine.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentReminderRecyclerItemBinding
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.repository.RepositoryHistoryImpl

class HomeFragmentAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentReminderHolder>() {

    private var reminderData: List<ReminderDTO> = listOf()
    private val repositoryHistoryImpl: RepositoryHistoryImpl by lazy { RepositoryHistoryImpl() }

    @SuppressLint("NotifyDataSetChanged")
    fun setReminder(data: List<ReminderDTO>) {
        this.reminderData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentReminderHolder {
        return HomeFragmentReminderHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_reminder_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeFragmentReminderHolder, position: Int) {
        holder.bind(this.reminderData[position])
    }

    override fun getItemCount(): Int {
        return if (reminderData.size > 3)
            3
        else reminderData.size
    }

    inner class HomeFragmentReminderHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(reminderDTO: ReminderDTO) {
            FragmentReminderRecyclerItemBinding.bind(itemView).run {
                nameMedicament.text = reminderDTO.nameMedicament
                howApply.text = reminderDTO.howApply
                applicationDays.text = applicationDaysConvert(reminderDTO.applicationDays)
                switchBtn.isChecked = reminderDTO.switch
                time1.text =
                    reminderDTO.time[0]//"${reminderDTO.time[0].hour}:${reminderDTO.time[0].minute} ${reminderDTO.time[0].AMorPM}"
                time2.text = if (reminderDTO.time.size > 1)
                    "And more ${reminderDTO.time.size - 1}"
                else ""

                switchBtn.setOnClickListener {
                    repositoryHistoryImpl.updateMedicaments(
                        ReminderDTO(
                            reminderDTO.nameMedicament,
                            reminderDTO.howApply,
                            reminderDTO.applicationDays, switchBtn.isChecked,
                            reminderDTO.time,
                            reminderDTO.details
                        )
                    )
                    listener.onItemClick(reminderDTO,switchBtn.isChecked)
                }
            }
        }

        private fun applicationDaysConvert(applicationDays: List<String>): String {
            var result = ""
            if (applicationDays[0].toBoolean())
                result += "Sun, "
            if (applicationDays[1].toBoolean())
                result += "Mon, "
            if (applicationDays[2].toBoolean())
                result += "Tue, "
            if (applicationDays[3].toBoolean())
                result += "Wed, "
            if (applicationDays[4].toBoolean())
                result += "Thu, "
            if (applicationDays[5].toBoolean())
                result += "Fri, "
            if (applicationDays[6].toBoolean())
                result += "Sat, "
            return result.dropLast(2)
        }
    }
}