package com.example.minutemedicine.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentHomeBinding
import com.example.minutemedicine.viewmodel.AppStateMedicineBD
import com.example.minutemedicine.viewmodel.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }

    private val adapter: HomeFragmentAdapter by lazy { HomeFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
    }

    private fun init() {
        binding.reminderFragmentRecyclerView.adapter = adapter
        binding.addContainer.setOnClickListener {
            requireActivity().findViewById<ViewPager>(R.id.viewPager).currentItem = 2
        }
        binding.showAllBtn.setOnClickListener {
            requireActivity().findViewById<ViewPager>(R.id.viewPager).currentItem = 3
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appStateDB: Any) {
        when (appStateDB) {
            is AppStateMedicineBD.Success -> {
                if (appStateDB.medicineInfoHistoryData.isEmpty()) {
                    binding.addContainer.visibility = View.VISIBLE
                    binding.todayReminders.visibility = View.GONE
                } else {
                    adapter.setReminder(appStateDB.medicineInfoHistoryData)
                }
                if (appStateDB.medicineInfoHistoryData.size > 3) {
                    binding.moreText.text = "And more ${appStateDB.medicineInfoHistoryData.size - 3}"
                }
            }
            else -> {}
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}