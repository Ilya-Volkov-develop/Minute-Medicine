package com.example.minutemedicine.view.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentTimeAlarmRecyclerItemBinding

class AddTimeAdapter : RecyclerView.Adapter<AddTimeAdapter.AddTimeHolder>() {

    private var data: MutableList<String> = mutableListOf()

    fun addTime(data: List<String>, position: Int) {
        this.data = data as MutableList<String>
        if (data.isNotEmpty()) {
            notifyItemInserted(position)
        } else {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTimeHolder {
        return AddTimeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_time_alarm_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddTimeHolder, position: Int) {
        holder.bind(this.data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class AddTimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(time: String) {
            FragmentTimeAlarmRecyclerItemBinding.bind(itemView).run {
                hour.text =  StringBuilder(time).apply {
                    insert(3,' ')
                    insert(3,' ')
                    insert(2,' ')
                    insert(2,' ')
                }.toString()

                trash.setOnClickListener {
                    data.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }
}