package com.example.minutemedicine.view.browse

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentBrowseRecyclerItemBinding
import com.example.minutemedicine.model.HealthDTO
import com.example.minutemedicine.repository.RepositoryHistoryImpl
import com.example.minutemedicine.utils.ITEM_CLOSE
import com.example.minutemedicine.utils.ITEM_OPEN
import java.util.*


class BrowseRecyclerViewAdapter : RecyclerView.Adapter<BrowseRecyclerViewAdapter.ViewHolder>(),
    Filterable {

    private var data: MutableList<Triple<Int, String, String>> = mutableListOf()
    private var filteredData: MutableList<Triple<Int, String, String>> = mutableListOf()
    private val repositoryHistoryImpl: RepositoryHistoryImpl by lazy { RepositoryHistoryImpl() }

    fun setBrowse(data: List<Triple<Int, String, String>>) {
        this.data = data as MutableList<Triple<Int, String, String>>
        this.filteredData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_browse_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Triple<Int, String, String>) {
            FragmentBrowseRecyclerItemBinding.bind(itemView).run {
                title.text = data.second
                edit.hint = data.second
                edit.setText(data.third)
                root.setOnClickListener {
                    this@BrowseRecyclerViewAdapter.data[layoutPosition] =
                        this@BrowseRecyclerViewAdapter.data[layoutPosition].let {
                            val currentState = if (it.first == ITEM_CLOSE) ITEM_OPEN else ITEM_CLOSE
                            Triple(currentState, it.second, it.third)
                        }
                    notifyItemChanged(layoutPosition)
                }
                edit.visibility = if (data.first == ITEM_CLOSE) View.GONE else View.VISIBLE
                if (data.first == ITEM_OPEN)
                    ObjectAnimator.ofFloat(arrow, View.ROTATION, 0f, 90f).start()
                else
                    ObjectAnimator.ofFloat(arrow, View.ROTATION, 90f, 0f).start()
                edit.setOnFocusChangeListener { view, b ->
                    if (!b){
                        repositoryHistoryImpl.updateHealth(HealthDTO(title.text.toString(),edit.text.toString()))
                        this@BrowseRecyclerViewAdapter.data[layoutPosition] =
                            this@BrowseRecyclerViewAdapter.data[layoutPosition].let {
                                Triple(it.first, it.second, edit.text.toString())
                            }
                        notifyItemChanged(layoutPosition)
                    }
                }
            }

        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                data = if (charSequence == "") {
                    filteredData
                } else {
                    val resultFilteredNotes: MutableList<Triple<Int, String, String>> = mutableListOf()
                    filteredData.forEach {
                        if (it.second.lowercase(Locale.ROOT).contains(
                                charSequence.toString().lowercase(Locale.ROOT))
                            || it.third.lowercase(Locale.ROOT).contains(
                                charSequence.toString().lowercase(Locale.ROOT))
                        ) resultFilteredNotes.add(it)
                    }
                    resultFilteredNotes

                }
                val filterResults = FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                data = filterResults?.values as MutableList<Triple<Int, String, String>>
                notifyDataSetChanged()
            }

        }
    }

}