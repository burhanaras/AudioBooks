package com.listenhub.audiobooksapp.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.presentation.ui.bestsellers.BestSellersFragment
import com.listenhub.audiobooksapp.presentation.ui.home.HomeFragment
import com.listenhub.audiobooksapp.presentation.ui.samples.SamplesFragment
import com.listenhub.audiobooksapp.presentation.ui.nowplaying.bottomsheet.NowPlayingBottomSheetFragment
import com.listenhub.audiobooksapp.presentation.ui.search.SearchFragment
import com.listenhub.audiobooksapp.presentation.ui.showall.ShowAllFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SamplesFragment.HomeFragmentInteractionListener {

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
            R.id.navigation_bestsellers -> {
                BestSellersFragment.newInstance()
            }
            R.id.navigation_search -> {
                SearchFragment.newInstance()
            }
            else -> HomeFragment.newInstance()
        }

        replaceFragment(fragment, tag)


    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        // show/hide fragment
        val transaction = supportFragmentManager.beginTransaction()
        when {
            !fragment.isAdded -> transaction.add(R.id.fragmentContainer, fragment, tag)
            else -> transaction.replace(R.id.fragmentContainer, fragment)
        }

        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, HomeFragment.newInstance())
            .commit()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setObservers()
        fabMiniPlayer.setOnClickListener { viewModel.onClickMiniEqualizedFab() }

    }

    private fun setObservers() {
        viewModel.goToPlayingNowActivity.observe(this, Observer {
            //    it?.let { audioBook -> startActivity(NowPlayingActivity.newIntent(this, audioBook)) }

            val bottomSheet = NowPlayingBottomSheetFragment.newInstance(it)
            bottomSheet.show(
                supportFragmentManager,
                NowPlayingBottomSheetFragment.TAG
            )
        })

        viewModel.fabMiniEqualizerVisibility.observe(this, Observer {
            it?.let { visibilityAndAnimatingStatus ->
                if (visibilityAndAnimatingStatus.first) fabMiniPlayer.visibility = View.VISIBLE
                else fabMiniPlayer.visibility = View.GONE

                if (visibilityAndAnimatingStatus.second && visibilityAndAnimatingStatus.first) miniEqualizer.animateBars()
                else miniEqualizer.stopBars()
            }
        })
    }

    override fun onCategorySelected(categoryId: String) {
        replaceFragment(ShowAllFragment.newInstance(categoryId), ShowAllFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_purchase -> {
            }
            R.id.action_search -> {
                navView.selectedItemId = R.id.navigation_search
            }
        }
        return true
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment is SamplesFragment) {
            super.onBackPressed()
        } else {
            navView.selectedItemId = R.id.navigation_home
        }
    }

    companion object {
        fun newIntent(callerContext: Context) = Intent(callerContext, MainActivity::class.java)
    }
}
