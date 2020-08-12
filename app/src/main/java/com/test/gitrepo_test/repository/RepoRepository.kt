package com.test.gitrepo_test.repository

import androidx.lifecycle.LiveData
import com.test.gitrepo_test.database.DatabaseRepo
import com.test.gitrepo_test.database.RepoDatabase
import com.test.gitrepo_test.network.APIServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepoRepository(private val Apiservices: APIServices, private val database: RepoDatabase) {
    suspend fun refreshRepos(){
        withContext(Dispatchers.IO){
            Timber.d("refresh repos is called");
            val reposList = Apiservices.getRepoList().await()
            database.repoDao.insertAll(reposList)
        }
    }

    val results: LiveData<List<DatabaseRepo>> = database.repoDao.getLocalDBRepo()


}