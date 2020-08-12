package com.test.gitrepo_test.di


import android.app.Application
import androidx.room.Room
import com.test.gitrepo_test.database.RepoDao
import com.test.gitrepo_test.database.RepoDatabase

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): RepoDatabase {
        return Room.databaseBuilder(application, RepoDatabase::class.java, "repos")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: RepoDatabase): RepoDao {
        return database.repoDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}
