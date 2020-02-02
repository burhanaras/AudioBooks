package com.listenhub.audiobooksapp.presentation.ui.splash

import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.presentation.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.goToHomeScreen.observe(this, Observer {
            startActivity(MainActivity.newIntent(this))
            finish()
        })

        showBgVideo()
    }

    private fun showBgVideo() {
        val uriPath = "android.resource://" + packageName + "/" + R.raw.video
        videoView.setVideoURI(Uri.parse(uriPath))
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        videoView.start()

    }
}
