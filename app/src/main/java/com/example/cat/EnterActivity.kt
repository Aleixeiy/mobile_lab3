package com.example.cat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*

class EnterActivity : AppCompatActivity() {
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        pref = getSharedPreferences("users", Context.MODE_PRIVATE)
        var users = findViewById<Spinner>(R.id.users)
        var names = arrayOf("нет")
        pref?.all?.keys?.forEach { it ->
            names = names.plus(it)
        }
        val arrayAdapter = ArrayAdapter(this, R.layout.spinner_list, names)
        users.adapter = arrayAdapter
        users.setSelection(0)
        val name = findViewById<EditText>(R.id.name)
        name.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
            {
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
            else
            {
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    fun onEnterClick(view: View)
    {
        var et = findViewById<EditText>(R.id.name)
        var name = et.text.toString()
        var users = findViewById<Spinner>(R.id.users)
        if (users.selectedItem.toString() != "нет")
            name = users.selectedItem.toString()

        if (name != "")
        {
            val editor = pref?.edit()
            editor?.putString(name, name)
            editor?.apply()

            val loadIntent = Intent(this, LoadActivity::class.java)
            loadIntent.putExtra("name", name)
            startActivity(loadIntent)
        }
    }
}