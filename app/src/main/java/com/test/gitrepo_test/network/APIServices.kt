package com.test.gitrepo_test.network

import com.test.gitrepo_test.database.DatabaseRepo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface APIServices{
    @GET(API_CALLS.REPO_LIST)
    fun getRepoList(): Deferred<List<DatabaseRepo>>
}