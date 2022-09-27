package com.example.minutemedicine.view.clock

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.work.WorkManager
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentClockBinding
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.repository.CreateWorker
import com.example.minutemedicine.viewmodel.AppStateMedicineBD
import com.example.minutemedicine.viewmodel.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClockFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!
    private val adapter: ClockFragmentAdapter by lazy { ClockFragmentAdapter(this) }
    private val historyViewModel: HistoryViewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }
    private val createWorker: CreateWorker by lazy { CreateWorker(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentClockBinding.inflate(inflater, container, false)
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            historyViewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
        historyViewModel.getAllMedicineHistory()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initSearch()
    }

    private fun init() {
        binding.clockFragmentRecyclerView.adapter = adapter
        binding.addContainer.setOnClickListener {
            requireActivity().findViewById<ViewPager>(R.id.viewPager).currentItem = 2
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appStateDB: Any) {
        when (appStateDB) {
            is AppStateMedicineBD.Error -> {}
            is AppStateMedicineBD.Success -> {
                if (appStateDB.medicineInfoHistoryData.isEmpty()) {
                    binding.addContainer.visibility = View.VISIBLE
                    binding.todayReminders.visibility = View.GONE
                } else if (!appStateDB.delete) {
                    adapter.setTime(appStateDB.medicineInfoHistoryData)
                }
            }
            else -> {}
        }
    }

    private fun initSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ClockFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClickGetSize(size: Int) {
        if (size == 0) {
            binding.addContainer.visibility = View.VISIBLE
            binding.todayReminders.visibility = View.GONE
        }
    }

    override fun onItemClickWorker(reminder: ReminderDTO, isChecked: Boolean) {
        if (isChecked){
            createWorker.createNotification(reminder)
        } else {
            WorkManager.getInstance(requireContext()).cancelAllWorkByTag(reminder.nameMedicament)
        }
    }
}