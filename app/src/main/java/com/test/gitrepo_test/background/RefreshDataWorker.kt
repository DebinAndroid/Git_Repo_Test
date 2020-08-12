package com.test.gitrepo_test.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.test.gitrepo_test.repository.RepoRepository
import timber.log.Timber

/**
 * WorkManager automatically calls Worker.doWork() on a background thread.
 * Periodically syncing application data with a server
 */
class RefreshDataWorker(private val repoRepository: RepoRepository,
                        appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME="com.test.gitrepo_test3.background.RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        /**
         * Sync the backend API data with local database even if user is not using the app or device restarts
         */
        try{
            repoRepository.refreshRepos()
            Timber.d("WorkManager: sync in progress")
        }
        catch (e:Exception){
            Timber.e("WorkManager error: ${e.localizedMessage}")
            return Result.retry()
        }
        return Result.success()
    }
}