package com.example.minutemedicine.view.clock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentClockRecyclerItemBinding
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.repository.RepositoryHistoryImpl
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class ClockFragmentAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<ClockFragmentAdapter.ViewHolder>(),Filterable
{

    private var data: MutableList<ReminderDTO> = mutableListOf()
    private var filteredData: MutableList<ReminderDTO> = mutableListOf()
    private val repositoryHistoryImpl: RepositoryHistoryImpl by lazy { RepositoryHistoryImpl() }

    fun setTime(data: List<ReminderDTO>) {
        this.data = data as MutableList<ReminderDTO>
        this.filteredData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_clock_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(reminderDTO: ReminderDTO) {
            FragmentClockRecyclerItemBinding.bind(itemView).run {
                nameMedicament.text = reminderDTO.nameMedicament
                howApply.text = reminderDTO.howApply
                applicationDays.text = applicationDaysConvert(reminderDTO.applicationDays)
                switchBtn.isChecked = reminderDTO.switch
                time1.text = timeConvert(reminderDTO.time)
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
                    listener.onItemClickWorker(reminderDTO, switchBtn.isChecked)
                }
                deleteTime.setOnClickListener {
                    data.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                    repositoryHistoryImpl.deleteMedicaments(reminderDTO)
                    listener.onItemClickGetSize(data.size)
                    listener.onItemClickWorker(reminderDTO, false)
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
        private fun timeConvert(time: List<String>): String {
            var result = ""
            time.forEach {
                result += "$it\n"
            }
            return result
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                data = if (charSequence == "") {
                    filteredData
                } else {
                    val resultFilteredTimes: MutableList<ReminderDTO> = mutableListOf()
                    filteredData.forEach {
                        if (it.nameMedicament.lowercase(Locale.ROOT).contains(
                                charSequence.toString().lowercase(Locale.ROOT))
                            || it.howApply.lowercase(Locale.ROOT).contains(
                                charSequence.toString().lowercase(Locale.ROOT))
                        ) resultFilteredTimes.add(it)
                    }
                    resultFilteredTimes

                }
                val filterResults = FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                data = filterResults?.values as MutableList<ReminderDTO>
                notifyDataSetChanged()
            }

        }
    }

}