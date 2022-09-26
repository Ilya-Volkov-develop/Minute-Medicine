package com.example.minutemedicine.view.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentBrowseBinding
import com.example.minutemedicine.model.HealthDTO
import com.example.minutemedicine.utils.ITEM_CLOSE
import com.example.minutemedicine.viewmodel.AppStateHealthBD
import com.example.minutemedicine.viewmodel.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private val adapter: BrowseRecyclerViewAdapter by lazy { BrowseRecyclerViewAdapter() }
    private val historyViewModel: HistoryViewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            historyViewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
        historyViewModel.getAllHealthHistory()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initSearch()
    }

    private fun renderData(appStateDB: Any?) {
        when(appStateDB){
            is AppStateHealthBD.Success ->{
                if (appStateDB.healthInfoHistoryData.isEmpty()){
                    createTableHealth()
                } else {
                    val drowseData = mutableListOf<Triple<Int, String, String>>()
                    appStateDB.healthInfoHistoryData.forEach {
                        drowseData.add(Triple(ITEM_CLOSE, it.nameHealth, it.textHealth))
                    }
                    adapter.setBrowse(drowseData)
                }
            }
            else -> {}
        }
    }

    private fun init() {
        binding.browseRecyclerView.adapter = adapter
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

    private fun createTableHealth() {
        listOf(
            getString(R.string.symptoms),
            getString(R.string.activity),
            getString(R.string.heart),
            getString(R.string.movement),
            getString(R.string.hearing),
            getString(R.string.nutrition),
            getString(R.string.blood_circulation),
            getString(R.string.sleep),
            getString(R.string.insulin_doses),
            getString(R.string.inhaler),
            getString(R.string.drinking_alcohol),
            getString(R.string.anemia),
            getString(R.string.chronic_diseases),
            getString(R.string.osteoporosis)
        ).forEach {
            historyViewModel.saveHealth(HealthDTO(it,""))
        }
        historyViewModel.getAllHealthHistory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = BrowseFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        adapter.filter.filter("")
    }
}