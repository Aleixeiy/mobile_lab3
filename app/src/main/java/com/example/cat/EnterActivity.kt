package com.example.cat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
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

        if (nameIsValid(name))
        {
            var passwordField = findViewById<EditText>(R.id.password)
            var password1 = passwordField.text.toString()
            var password2 = pref?.getString(name, "")

            if ((password1 == password2) && (password2 != ""))
            {
                val loadIntent = Intent(this, MainActivity::class.java)
                loadIntent.putExtra("name", name)
                startActivity(loadIntent)
            } else
            {
                val text = "Неверный логин или пароль"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        } else
        {
            val text = "Некорректный mail"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    fun onRegClick(view: View)
    {
        var et = findViewById<EditText>(R.id.name)
        var name = et.text.toString()

        var password = pref?.getString(name, "")
        if (password != "")
        {
            val text = "Данный пользователь уже существует"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        } else
        if (nameIsValid(name))
        {
            var passwordField = findViewById<EditText>(R.id.password)
            var password = passwordField.text.toString()

            if (password.length < 3)
            {
                val text = "Пароль слишком короткий"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                val editor = pref?.edit()
                editor?.putString(name, password)
                editor?.apply()

                val loadIntent = Intent(this, MainActivity::class.java)
                loadIntent.putExtra("name", name)
                startActivity(loadIntent)
            }
        }
        else
        {
            val text = "Некорректный mail"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    fun nameIsValid(name: String): Boolean
    {
        return !TextUtils.isEmpty(name) && android.util.Patterns.EMAIL_ADDRESS.matcher(name).matches()
    }
}