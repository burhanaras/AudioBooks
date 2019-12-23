package com.burhan.audiobooksapp.presentation.ui.splash

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.presentation.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.goToHomeScreen.observe(this, Observer {
            startActivity(MainActivity.newIntent(this))
            finish()
        })
        viewModel.goToWalkThrough.observe(this, Observer {
            showBgVideo()
        })
    }

    private fun showBgVideo() {
        rlVideoBg.visibility = View.VISIBLE
        val uriPath = "android.resource://" + packageName + "/" + R.raw.video
        videoView.setVideoURI(Uri.parse(uriPath))
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        videoView.start()

    }
}
