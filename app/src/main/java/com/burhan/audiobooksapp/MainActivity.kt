package com.burhan.audiobooksapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.presentation.ui.MainActivityViewModel
import com.burhan.audiobooksapp.presentation.ui.dashboard.DashboardFragment
import com.burhan.audiobooksapp.presentation.ui.home.HomeFragment
import com.burhan.audiobooksapp.presentation.ui.player.NowPlayingActivity
import com.burhan.audiobooksapp.presentation.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            loadFragment(item.itemId)
            true
        }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment: Fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_home -> {
                HomeFragment.newInstance()
            }
            R.id.navigation_dashboard -> {
                DashboardFragment.newInstance()
            }
            R.id.navigation_notifications -> {
                SettingsFragment.newInstance()
            }
            else -> HomeFragment.newInstance()
        }

        // show/hide fragment
        val transaction = supportFragmentManager.beginTransaction()

        if (viewModel.lastActiveFragmentTag != null) {
            val lastFragment =
                supportFragmentManager.findFragmentByTag(viewModel.lastActiveFragmentTag)
            if (lastFragment != null)
                transaction.hide(lastFragment)
        }

        when {
            !fragment.isAdded -> transaction.add(R.id.fragmentContainer, fragment, tag)
            else -> transaction.show(fragment)
        }

        transaction.commit()
        viewModel.lastActiveFragmentTag = tag


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, HomeFragment.newInstance()).commit()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setObservers()
        fabMiniPlayer.setOnClickListener { startActivity(NowPlayingActivity.newIntent(this, null)) }
    }

    private fun setObservers() {
        viewModel.fabMiniPlayerVisibility.observe(this, Observer {
            it?.let { visible ->
                if (visible) fabMiniPlayer.show()
                else fabMiniPlayer.hide()
            }
        })
    }
}
