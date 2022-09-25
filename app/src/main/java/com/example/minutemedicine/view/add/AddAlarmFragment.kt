package com.example.minutemedicine.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentAddBinding
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.viewmodel.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddAlarmFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val adapter: AddTimeAdapter by lazy { AddTimeAdapter() }
    private var timeList = mutableListOf<String>()
    private val historyViewModel: HistoryViewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            historyViewModel.getLiveData().observe(viewLifecycleOwner) {}
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repeatingRecycler.adapter = adapter
        workTimePiker()
        workBtn()
//        binding.spinnerTitle.isEnabled = false
    }

    private fun fillInfo(reminder: ReminderDTO) {
        with(binding){
            spinnerTitle.visibility = View.GONE
            textTitle.text = reminder.nameMedicament
            textTitle.visibility = View.VISIBLE
            editMedicine.setText(reminder.howApply)
            editDetails.setText(reminder.details)
            timeList = reminder.time as MutableList<String>
            adapter.addTime(timeList,0)
            checkBoxSunday.isChecked = reminder.applicationDays[0].toBoolean()
            checkBoxMonday.isChecked = reminder.applicationDays[1].toBoolean()
            checkBoxTuesday.isChecked = reminder.applicationDays[2].toBoolean()
            checkBoxWednesday.isChecked = reminder.applicationDays[3].toBoolean()
            checkBoxThursday.isChecked = reminder.applicationDays[4].toBoolean()
            checkBoxFriday.isChecked = reminder.applicationDays[5].toBoolean()
            checkBoxSaturday.isChecked = reminder.applicationDays[6].toBoolean()
            nestedScrollView.scrollY = 0
        }
    }

    private fun workBtn() {
        with(binding){
            saveBtn.setOnClickListener { saveInfo() }
            saveBtnMini.setOnClickListener { saveInfo() }
            x.setOnClickListener { exit() }
            cancelBtn.setOnClickListener { exit() }
        }
    }

    private fun saveInfo() {
        with(binding) {
            if (editMedicine.text.toString() == "") {
                Toast.makeText(requireContext(), "Введите editMedicine", Toast.LENGTH_SHORT).show()
            } else if (editDetails.text.toString() == "") {
                Toast.makeText(requireContext(), "Введите editDetails", Toast.LENGTH_SHORT).show()
            } else if (timeList.size == 0) {
                Toast.makeText(requireContext(), "Введите timeList", Toast.LENGTH_SHORT).show()
            } else if (checkBoxWeek()) {
                Toast.makeText(requireContext(), "Введите checkBox", Toast.LENGTH_SHORT).show()
            } else {
                historyViewModel.saveMedicine(ReminderDTO(
                    spinnerTitle.selectedItem.toString(),
                    editMedicine.text.toString(),
                    checkBox(), true, timeList,
                    editDetails.text.toString()
                ))
                exit()
            }
        }
    }

    private fun checkBox(): List<String> {
        val listWeek =
            mutableListOf("false", "false", "false", "false", "false", "false", "false")
        if (binding.checkBoxSunday.isChecked)
            listWeek[0] = "true"
        if (binding.checkBoxMonday.isChecked)
            listWeek[1] = "true"
        if (binding.checkBoxTuesday.isChecked)
            listWeek[2] = "true"
        if (binding.checkBoxWednesday.isChecked)
            listWeek[3] = "true"
        if (binding.checkBoxThursday.isChecked)
            listWeek[4] = "true"
        if (binding.checkBoxFriday.isChecked)
            listWeek[5] = "true"
        if (binding.checkBoxSaturday.isChecked)
            listWeek[6] = "true"
        return listWeek
    }

    private fun checkBoxWeek(): Boolean {
        with(binding) {
            if (!checkBoxSunday.isChecked && !checkBoxMonday.isChecked &&
                !checkBoxTuesday.isChecked && !checkBoxWednesday.isChecked &&
                !checkBoxThursday.isChecked && !checkBoxFriday.isChecked &&
                !checkBoxSaturday.isChecked
            )
                return true
        }
        return false
    }

    //region time
    private fun workTimePiker() {
        binding.addBtn.setOnClickListener {
            var check = false
            val newTime = "${timeHour(binding.timePicker.hour.toString())}:" +
                    "${timeMinute(binding.timePicker.minute.toString())} " +
                    timeAMPM(binding.timePicker.hour.toString())
            if (timeList.size != 0) {
                timeList.forEach {
                    if (newTime == it) {
                        Toast.makeText(
                            requireContext(),
                            "Такое время уже существует",
                            Toast.LENGTH_SHORT
                        ).show()
                        check = true
                    }
                }
            }
            if (!check) {
                var position = 0
                timeList.add(newTime)
                timeList.sortWith(
                    compareBy(
                        { it.length }, { it }
                    )
                )
                for (i in 0 until timeList.size) {
                    if (timeList[i] == newTime) {
                        position = i
                    }
                }
                adapter.addTime(timeList, position)
            }
        }
    }


    private fun timeAMPM(time: String): String {
        return if (time.toInt() >= 12) {
            "PM "
        } else {
            "AM"
        }
    }

    private fun timeHour(time: String): String {
        if (time.length == 1)
            return "0$time"
        else if (time.toInt() < 12)
            return time
        else if ((time.toInt() - 12).toString().length == 1)
            return "0${(time.toInt() - 12)}"
        else if (time.toInt() - 12 < 12)
            return "${(time.toInt() - 12)}"
        else if (time.toInt() - 12 == 12)
            return "00"
        return time
    }

    private fun timeMinute(time: String): String {
        return if (time.length == 1)
            "0$time"
        else
            time
    }
    //endregion

    private fun exit() {
        requireActivity().findViewById<ViewPager>(R.id.viewPager).currentItem = 0
        with(binding){
            spinnerTitle.setSelection(0)
            editMedicine.text.clear()
            editDetails.text.clear()
            timeList = mutableListOf()
            adapter.addTime(timeList,0)
            checkBoxSunday.isChecked = false
            checkBoxMonday.isChecked = false
            checkBoxTuesday.isChecked = false
            checkBoxWednesday.isChecked = false
            checkBoxThursday.isChecked = false
            checkBoxFriday.isChecked = false
            checkBoxSaturday.isChecked = false
            nestedScrollView.scrollY = 0
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddAlarmFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}