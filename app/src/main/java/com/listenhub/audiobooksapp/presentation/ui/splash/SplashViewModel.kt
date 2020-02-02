package com.listenhub.audiobooksapp.presentation.ui.splash

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.listenhub.audiobooksapp.domain.usecase.DbVersionCheckUseCase
import com.listenhub.audiobooksapp.domain.usecase.download.DownloadAllDataUseCase

/**
 * Developed by tcbaras on 2019-12-16.
 */
class SplashViewModel(app: Application) : AndroidViewModel(app) {

    internal var goToHomeScreen: MutableLiveData<Boolean> = MutableLiveData()

    init {

        val dbVersionCheckUseCase = DbVersionCheckUseCase(app)
        dbVersionCheckUseCase.checkIfDbIsEmpty { dbIsEmpty ->
            if (dbIsEmpty) {
                val downloadAllDataUseCase =
                    DownloadAllDataUseCase(app)
                downloadAllDataUseCase.executeFromJson {
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
