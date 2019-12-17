package com.burhan.audiobooksapp.presentation

import android.app.Application
import com.burhan.audiobooksapp.BuildConfig
import com.facebook.stetho.Stetho

/**
 * Developed by tcbaras on 2019-12-16.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}