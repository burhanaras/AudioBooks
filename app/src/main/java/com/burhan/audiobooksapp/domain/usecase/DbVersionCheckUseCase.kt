package com.burhan.audiobooksapp.domain.usecase

import android.content.Context
import com.burhan.audiobooksapp.data.db.AppDatabase
import com.burhan.audiobooksapp.data.entity.DbMetaData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DbVersionCheckUseCase(val context: Context) {
    private val fireStoreDb = FirebaseFirestore.getInstance()

    fun checkIfDbIsEmpty(callback: (Boolean) -> Unit) {
        GlobalScope.launch {
            val count = AppDatabase.getInstance(context).audioBookDao().getAudioBookTotalcount()
            when {
                count > 0 -> {
                    callback(true)
                }
                else -> {
                    callback(false)
                }
            }
        }
    }

    fun checkIfDbNeedsToBeUpdated(
        callback: (
            fullAudioBooksNeedUpdate: Boolean,
            sampleAudioBooksNeedUpdate: Boolean,
            newSampleAudioBooksNeedUpdate: Boolean
        ) -> Unit
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            val dbMetaData = AppDatabase.getInstance(context).audioBookDao().getDbMetaData()
            if (dbMetaData != null) {
                getFireBaseDbVersion { fullAudioBooksDbVersion, sampleAudioBooksDbVersion, newSampleAudioBooksDbVersion ->
                    callback(
                        fullAudioBooksDbVersion == dbMetaData.fullAudioBooksVersion,
                        sampleAudioBooksDbVersion == dbMetaData.sampleAudioBooksVersion,
                        newSampleAudioBooksDbVersion == dbMetaData.newSampleAudioBooksVersion
                    )
                }
            } else {
                callback(true, true, true)
            }
        }
    }

    private fun getFireBaseDbVersion(
        callback: (
            fullAudioBooksDbVersion: String,
            sampleAudioBooksDbVersion: String,
            newSampleAudioBooksDbVersion: String
        ) -> Unit
    ) {
        fireStoreDb.collection("DbMetaData").get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.let { result ->
                    if (result.documents.isNotEmpty()) {
                        val fullAudioBooksDbVersion =
                            result.documents[0]["FullAudioBooksDbVersion"].toString()
                        val sampleAudioBooksDbVersion =
                            result.documents[0]["SampleAudioBooksDbVersion"].toString()
                        val newSampleAudioBooksDbVersion =
                            result.documents[0]["NewSampleAudioBooksDbVersion"].toString()
                        callback(
                            fullAudioBooksDbVersion,
                            sampleAudioBooksDbVersion,
                            newSampleAudioBooksDbVersion
                        )
                    }
                }
            }
        }
    }

    fun updateLocalDbVersion() {
        getFireBaseDbVersion { fullAudioBooksDbVersion, sampleAudioBooksDbVersion, newSampleAudioBooksDbVersion ->
            GlobalScope.launch(Dispatchers.IO) {
                val dbMetaData = DbMetaData(
                    dbVersion = "",
                    fullAudioBooksVersion = fullAudioBooksDbVersion,
                    sampleAudioBooksVersion = sampleAudioBooksDbVersion,
                    newSampleAudioBooksVersion = newSampleAudioBooksDbVersion
                )
                AppDatabase.getInstance(context).audioBookDao().insertDbMetaData(dbMetaData)
            }
        }
    }
}