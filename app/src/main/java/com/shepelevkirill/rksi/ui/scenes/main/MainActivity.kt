package com.shepelevkirill.rksi.ui.scenes.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.ui.scenes.schedule.ScheduleFragment
import com.shepelevkirill.rksi.ui.scenes.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainMvpView {
    @InjectPresenter lateinit var presenter: MainPresenter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_schedule -> {
                presenter.onSchedulePressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                presenter.onSettingsPressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                presenter.onSearchPressed()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
