package com.example.bebaagua.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bebaagua.ButtonSelected
import com.example.bebaagua.R
import com.example.bebaagua.databinding.ActivityMainBinding
import com.example.bebaagua.ui.fragments.NewsFragment
import com.example.bebaagua.ui.fragments.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsFragment = NewsFragment()
        val settingsFragment = SettingsFragment()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_news -> setCurrentFragment(newsFragment)
                R.id.nav_settings -> setCurrentFragment(settingsFragment)
            }
            true
        }

        loadSharedPreferences()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.constraint_layout, fragment)
            commit()
        }

    private fun loadSharedPreferences() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val reminder = sharedPreferences.getInt("Reminder", 0)
        val name = sharedPreferences.getString("name", "")
        Log.i(TAG, "Reminder is set to $reminder")
        lifecycleScope.launch(Dispatchers.Main) {
            val isNeverActive = reminder == ButtonSelected.NEVER.code
            Log.i(TAG, "Is never active? $isNeverActive")
            if(reminder != ButtonSelected.NEVER.code) {
                when(reminder) {
                    1 -> while(true) delay(3000L)
                    2 -> while(true) delay(10000L)
                    5 -> while(true) delay(50000L)
                }
                makeToast()
            }
        }
    }

    private fun makeToast() {
        Toast.makeText(this, "Beba água", Toast.LENGTH_SHORT).show()
    }
}