package com.burhan.audiobooksapp.presentation.ui.splash

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.burhan.audiobooksapp.domain.usecase.DbVersionCheckUseCase
import com.burhan.audiobooksapp.domain.usecase.DownloadAllDataUseCase

/**
 * Developed by tcbaras on 2019-12-16.
 */
class SplashViewModel(app: Application) : AndroidViewModel(app) {

    internal var goToHomeScreen: MutableLiveData<Boolean> = MutableLiveData()

    init {

        val dbVersionCheckUseCase = DbVersionCheckUseCase(app)
        dbVersionCheckUseCase.checkIfDbNeedsToBeUpdated { needsToBeUpdated ->
            if (needsToBeUpdated) {
                val downloadAllDataUseCase = DownloadAllDataUseCase(app)
                downloadAllDataUseCase.execute {
                    dbVersionCheckUseCase.updateLocalDbVersion()
                    goToHomeScreen.postValue(true)
                }
            } else {
                Handler().postDelayed(
                    {
                        goToHomeScreen.postValue(true)
                    }, 3000
                )
            }
        }
    }
}
