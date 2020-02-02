package com.listenhub.audiobooksapp.domain.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DownloadDBWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {
    override fun doWork(): Result {

        GlobalScope.launch(Dispatchers.IO) {

        }
        return Result.success()
    }
}