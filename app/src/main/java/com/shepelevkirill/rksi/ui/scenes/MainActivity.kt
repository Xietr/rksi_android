package com.shepelevkirill.rksi.ui.scenes

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.ui.scenes.schedule.ScheduleFragment
import com.shepelevkirill.rksi.ui.scenes.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val scheduleFragment: ScheduleFragment = ScheduleFragment()
    private val settingsFragment: SettingsFragment = SettingsFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_schedule -> {
                openFragment(scheduleFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                openFragment(settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(scheduleFragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
