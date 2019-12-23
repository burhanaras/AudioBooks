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

    fun checkIfDbNeedsToBeUpdated(callback: (needsToBeUpdated: Boolean) -> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            val dbMetaData = AppDatabase.getInstance(context).audioBookDao().getDbMetaData()
            if (dbMetaData != null) {
                val localDbVersion = dbMetaData.dbVersion
                getFireBaseDbVersion { fireBaseDbVersion ->
                    when (fireBaseDbVersion) {
                        YouDontHaveToUpdateIt -> callback(false)
                        else -> callback(fireBaseDbVersion != localDbVersion)
                    }
                }
            } else {
                callback(true)
            }
        }
    }

    private fun getFireBaseDbVersion(callback: (fireBaseDbVersion: String) -> Unit) {
        fireStoreDb.collection("DbMetaData").get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.let { result ->
                    if (result.documents.isNotEmpty()) {
                        val fireBaseDbVersion = result.documents[0]["DbVersion"].toString()
                        callback(fireBaseDbVersion)
                    } else {
                        callback(YouDontHaveToUpdateIt)
                    }

                }
            }
        }
    }

    fun updateLocalDbVersion() {
        getFireBaseDbVersion {
            GlobalScope.launch(Dispatchers.IO) {
                val dbMetaData = DbMetaData(dbVersion = it)
                AppDatabase.getInstance(context).audioBookDao().insertDbMetaData(dbMetaData)
            }
        }
    }

    companion object {
        val YouDontHaveToUpdateIt = "YouDon'tHaveToUpdateIt"
    }
}