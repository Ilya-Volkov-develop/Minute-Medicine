package com.example.minutemedicine.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.minutemedicine.model.ReminderDTO
import com.example.minutemedicine.worker.MyWorker
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class CreateWorker(val context: Context) {
    fun createNotification(result: ReminderDTO) {
        for (i in 0..6) {
            if (result.applicationDays[i].toBoolean()) {
                createWorkNotification(result, i + 1)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createWorkNotification(result: ReminderDTO, weekId: Int) {
        val timeWeek = getWeekTime(weekId)
        result.time.forEach {
            val data = Data.Builder()
                .putString("title", "Не забудьте принять лекарства от: ${result.nameMedicament}")
                .putString("desc", "${result.howApply} $it")
                .putInt("id", createId(result.nameMedicament))
                .build()
            val time = convertTime(it)
            val currentTime = SimpleDateFormat("HH:mm").format(Date())
            val now = LocalTime.parse(currentTime, DateTimeFormatter.ofPattern("H:m"))
            val from =
                LocalTime.parse("${time.first}:${time.second}", DateTimeFormatter.ofPattern("H:m"))
            var seconds = from.until(now, ChronoUnit.SECONDS)
            seconds = if (seconds.toInt() < 0) {
                abs(from.until(now, ChronoUnit.SECONDS))
            } else {
                if (timeWeek != 0) {
                    60 * 60 * 24 - from.until(now, ChronoUnit.SECONDS)
                } else {
                    60 * 60 * 24 * 7 - from.until(now, ChronoUnit.SECONDS)
                }
            }
            val myWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java,
                60 * 24 * 7,
                TimeUnit.MINUTES,
                (60 * 24 * 7 - 1),
                TimeUnit.MINUTES)
                .setInputData(data)
                .setInitialDelay(seconds + timeWeek, TimeUnit.SECONDS)
                .addTag(result.nameMedicament)
                .build()
            WorkManager.getInstance(context).enqueue(myWorkRequest)
            Log.d("mylog", "save ${result.nameMedicament} week = $weekId time = $time")
        }
    }

    private fun createId(nameMedicament: String): Int {
        return when (nameMedicament) {
            "Anaemia" -> 0
            "Anorexia" -> 1
            "Arrhythmia" -> 2
            "Insomnia" -> 3
            "Bronchitis" -> 4
            "Antritis" -> 5
            "Gastritis" -> 6
            "Gonorrhoea" -> 7
            "Diabetes" -> 8
            "Cough" -> 9
            else -> 10
        }
    }

    private fun convertTime(it: String): Pair<Int, Int> {
        val time = it.split(':', ' ')
        return if (time[2] == "PM") {
            Pair(time[0].toInt() + 12, time[1].toInt())
        } else {
            Pair(time[0].toInt(), time[1].toInt())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getWeekTime(i: Int): Int {
        var week = 0
        when (SimpleDateFormat("u").format(Date())) {
            "7" -> {
                when (i) {
                    1 -> week = 0
                    2 -> week = 60 * 60 * 24
                    3 -> week = 2 * 60 * 60 * 24
                    4 -> week = 3 * 60 * 60 * 24
                    5 -> week = 4 * 60 * 60 * 24
                    6 -> week = 5 * 60 * 60 * 24
                    7 -> week = 6 * 60 * 60 * 24
                }
            }
            "1" -> {
                when (i) {
                    1 -> week = 6 * 60 * 60 * 24
                    2 -> week = 0
                    3 -> week = 60 * 60 * 24
                    4 -> week = 2 * 60 * 60 * 24
                    5 -> week = 3 * 60 * 60 * 24
                    6 -> week = 4 * 60 * 60 * 24
                    7 -> week = 5 * 60 * 60 * 24
                }
            }
            "2" -> {
                when (i) {
                    1 -> week = 5 * 60 * 60 * 24
                    2 -> week = 6 * 60 * 60 * 24
                    3 -> week = 0
                    4 -> week = 60 * 60 * 24
                    5 -> week = 2 * 60 * 60 * 24
                    6 -> week = 3 * 60 * 60 * 24
                    7 -> week = 4 * 60 * 60 * 24
                }
            }
            "3" -> {
                when (i) {
                    1 -> week = 4 * 60 * 60 * 24
                    2 -> week = 5 * 60 * 60 * 24
                    3 -> week = 6 * 60 * 60 * 24
                    4 -> week = 0
                    5 -> week = 60 * 60 * 24
                    6 -> week = 2 * 60 * 60 * 24
                    7 -> week = 3 * 60 * 60 * 24
                }
            }
            "4" -> {
                when (i) {
                    1 -> week = 3 * 60 * 60 * 24
                    2 -> week = 4 * 60 * 60 * 24
                    3 -> week = 5 * 60 * 60 * 24
                    4 -> week = 6 * 60 * 60 * 24
                    5 -> week = 0
                    6 -> week = 60 * 60 * 24
                    7 -> week = 2 * 60 * 60 * 24
                }
            }
            "5" -> {
                when (i) {
                    1 -> week = 2 * 60 * 60 * 24
                    2 -> week = 3 * 60 * 60 * 24
                    3 -> week = 4 * 60 * 60 * 24
                    4 -> week = 5 * 60 * 60 * 24
                    5 -> week = 6 * 60 * 60 * 24
                    6 -> week = 0
                    7 -> week = 60 * 60 * 24
                }
            }
            "6" -> {
                when (i) {
                    1 -> week = 60 * 60 * 24
                    2 -> week = 2 * 60 * 60 * 24
                    3 -> week = 3 * 60 * 60 * 24
                    4 -> week = 4 * 60 * 60 * 24
                    5 -> week = 5 * 60 * 60 * 24
                    6 -> week = 6 * 60 * 60 * 24
                    7 -> week = 0
                }
            }
        }
        return week
    }
}