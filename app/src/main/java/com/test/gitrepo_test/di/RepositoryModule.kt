package com.test.gitrepo_test.di

import com.test.gitrepo_test.database.RepoDatabase
import com.test.gitrepo_test.network.APIServices
import com.test.gitrepo_test.repository.RepoRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideMoviesRepository(api: APIServices, dao: RepoDatabase): RepoRepository {
        return RepoRepository(api, dao)
    }
    single { provideMoviesRepository(get(), get()) }
}