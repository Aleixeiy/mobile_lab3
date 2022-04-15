package com.example.cat

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

class ProfileActivity : AppCompatActivity() {
    val IMAGE_PIC_CODE = 1000
    val PERMISSION_CODE = 1001
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        name = intent.getStringExtra("name")!!
        var pref = getSharedPreferences(name, Context.MODE_PRIVATE)

        var weight = pref?.getInt("weight", 0)
        var height = pref?.getInt("height", 0)
        var age = pref?.getInt("age", 0)
        var edt_age = findViewById<EditText>(R.id.edt_age)
        var edt_height = findViewById<EditText>(R.id.edt_height)
        var edt_weight = findViewById<EditText>(R.id.edt_weight)
        var edt_imt = findViewById<EditText>(R.id.edt_imt)
        edt_age.setText(age.toString())
        edt_height.setText(height.toString())
        edt_weight.setText(weight.toString())
        try
        {
            var imt: Float = (weight!! * 1000000 / height!! / height!!) / 100f
            edt_imt.setText(imt.toString())
        }
        catch(e: Exception)
        {
            edt_imt.setText("0")
        }

        var txt_email = findViewById<TextView>(R.id.txt_email)
        txt_email.text = name


        var pic = pref?.getString("pic", "")
        if ((pic != null) && (pic != ""))
        {
            var img_profile = findViewById<ImageView>(R.id.img_profile)
            img_profile.setImageURI(pic?.toUri())
        }

        edt_age.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                try
                {
                    val editor = pref?.edit()
                    editor?.putInt("age", edt_age.text.toString().toInt())
                    editor?.apply()
                }
                catch (e: Exception)
                {
                    val editor = pref?.edit()
                    editor?.putInt("age", 0)
                    editor?.apply()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        var img_profile = findViewById<ImageView>(R.id.img_profile)
        img_profile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ==
                    PackageManager.PERMISSION_DENIED)
                {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else
                {
                    pickImageFromGallery()
                }
            }
            else
            {
                pickImageFromGallery()
            }
        }

        edt_height.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                try
                {
                    val editor = pref?.edit()
                    editor?.putInt("height", edt_height.text.toString().toInt())
                    editor?.apply()
                }
                catch (e: Exception)
                {
                    val editor = pref?.edit()
                    editor?.putInt("height", 0)
                    editor?.apply()
                }

                var height = 1
                try
                {
                    height = edt_height.text.toString().toInt()
                }
                catch (e: Exception)
                {

                }
                var weight = 0
                try
                {
                    weight = edt_weight.text.toString().toInt()
                }
                catch (e: Exception)
                {

                }
                try
                {
                    var imt: Float = (weight * 1000000 / height / height) / 100f
                    edt_imt.setText(imt.toString())
                }
                catch(e: Exception)
                {
                    edt_imt.setText("0")
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        edt_weight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                try
                {
                    val editor = pref?.edit()
                    editor?.putInt("weight", edt_weight.text.toString().toInt())
                    editor?.apply()
                }
                catch (e: Exception)
                {
                    val editor = pref?.edit()
                    editor?.putInt("weight", 0)
                    editor?.apply()
                }

                var height = 1
                try
                {
                    height = edt_height.text.toString().toInt()
                }
                catch (e: Exception)
                {

                }
                var weight = 0
                try
                {
                    weight = edt_weight.text.toString().toInt()
                }
                catch (e: Exception)
                {

                }
                try
                {
                    var imt: Float = (weight * 1000000 / height / height) / 100f
                    edt_imt.setText(imt.toString())
                }
                catch(e: Exception)
                {
                    edt_imt.setText("0")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun pickImageFromGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PIC_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            PERMISSION_CODE ->
            {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery()
                }
                else
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PIC_CODE)
        {
            var pref = getSharedPreferences(name, Context.MODE_PRIVATE)
            val editor = pref?.edit()
            editor?.remove("pic")
            editor?.apply()
            editor?.putString("pic", data?.data.toString())
            editor?.apply()
            var img_profile = findViewById<ImageView>(R.id.img_profile)
            img_profile.setImageURI(data?.data)
        }
    }

    fun onMainClick(view: View)
    {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun onHoroscopeClick(view: View)
    {
        val horoscopeIntent = Intent(this, HoroscopeActivity::class.java)
        startActivity(horoscopeIntent)
    }
}