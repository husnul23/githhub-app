package com.example.githubapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//            Toast.makeText(activity, "active", Toast.LENGTH_SHORT).show()
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onResume() { super.onResume()
            getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() { super.onPause()
            getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
            val alarmOn = preferences.getBoolean(key, true)
            val alarmManager = AlarmManager()
            if (alarmOn) {
                alarmManager.setRepeatingAlarm(requireActivity(), AlarmManager.TYPE_REPEATING)
            } else {
                alarmManager.cancelAlarm(requireActivity(), AlarmManager.TYPE_REPEATING)
            }
        }
    }






}