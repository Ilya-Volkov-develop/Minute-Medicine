package com.example.minutemedicine.view

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.ActivityMainBinding
import com.example.minutemedicine.view.mainscreen.MainFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backBtnSys: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initColorTitle()
        init()
    }

    private fun initColorTitle() {
        val string = binding.title.text
        val stringArray = string.split(" ")
        val spannableString1 = SpannableString(stringArray[0])
        val spannableString2 = SpannableString(stringArray[1])
        val startIndex = 0
        val endIndex1 = stringArray[0].length
        val endIndex2 = stringArray[1].length
        val flag = 0
        val color1 = ContextCompat.getColor(this, R.color.light_blue)
        spannableString1.setSpan(ForegroundColorSpan(color1), startIndex, endIndex1, flag)
        val color2 = ContextCompat.getColor(this, R.color.dark_blue)
        spannableString2.setSpan(ForegroundColorSpan(color2), startIndex, endIndex2, flag)
        binding.title.text =
            TextUtils.concat(spannableString1, SpannableString(" "), spannableString2)
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText || v is SearchView) {
                v.clearFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onBackPressed() {
        if (backBtnSys + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(baseContext, "Нажмити ещё раз, чтобы выйти", Toast.LENGTH_SHORT)
            backToast!!.show()
        }
        backBtnSys = System.currentTimeMillis()
    }
}