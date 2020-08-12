package com.test.gitrepo_test

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.test.gitrepo_test.background.RefreshDataWorker
import com.test.gitrepo_test.di.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    private val applicationScope= CoroutineScope(Dispatchers.Default)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        /**
         * Start Koin
         */
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule, databaseModule))
        }

        // Start WorkManager
        delayedInit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setUpRecurringWork()// work performed by the Worker
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(false)
            .setRequiresDeviceIdle(true)
            .build()


        val repeatingRequest= PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        Timber.d("WorkManager: Work is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}