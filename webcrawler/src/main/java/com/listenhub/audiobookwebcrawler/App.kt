package com.listenhub.audiobookwebcrawler

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Developed by tcbaras on 2019-12-13.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}