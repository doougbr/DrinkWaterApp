package com.example.bebaagua.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.bebaagua.R
import com.example.bebaagua.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val buttonLogin = binding.buttonLogin
        val editTextName = binding.editTextName
        val checkbox = binding.checkbox

        loadSharedPreferences()

        editTextName.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                buttonLogin.apply {
                    setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.grey))
                    buttonLogin.isEnabled = false
                }
            } else {
                buttonLogin.apply {
                    buttonLogin.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.teal_700
                        )
                    )
                    buttonLogin.isEnabled = true
                    setOnClickListener {
                        if (checkbox.isChecked) {
                            saveToSharedPreferences()
                        }
                        goToMainActivity()
                    }
                }

            }
        }
    }

    private fun saveToSharedPreferences() {
        val editor = preferences.edit()
        val checkBox = binding.checkbox.isChecked
        val name = binding.editTextName.text.toString()

        editor.apply {
            putBoolean("autoLogin", checkBox)
            putString("name", name)
            apply()
        }
    }

    private fun loadSharedPreferences() {
        val checkBox = preferences.getBoolean("autoLogin", false)
        Log.d(TAG, "$checkBox")
        if (checkBox) {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}