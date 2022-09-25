package com.example.minutemedicine.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minutemedicine.databinding.FragmentReminderBinding
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.repository.RepositoryHistoryImpl
import java.text.SimpleDateFormat
import java.util.*

class ReminderFragment : Fragment() {

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private var listReminders = mutableListOf<ReminderDTO>()
    private val adapter: ReminderFragmentAdapter by lazy { ReminderFragmentAdapter() }
    private val repositoryHistoryImpl: RepositoryHistoryImpl by lazy { RepositoryHistoryImpl() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reminderFragmentRecyclerView.adapter = adapter
        init()

    }

    private fun init() {
        val sdf = SimpleDateFormat("EEEE,dd/LL/yyyy")
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        binding.data.text = dayOfTheWeek
        Thread {
            listReminders =
                repositoryHistoryImpl.getAllHistoryMedicaments() as MutableList<ReminderDTO>
            requireActivity().runOnUiThread {
                adapter.setReminder(listReminders)
            }
        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReminderFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}