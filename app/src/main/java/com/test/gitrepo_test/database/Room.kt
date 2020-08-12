package com.test.gitrepo_test.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RepoDao{
    @Query("SELECT * from DatabaseRepo")
    fun getLocalDBRepo(): LiveData<List<DatabaseRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( repos: List<DatabaseRepo>)
}

@Database(entities = [DatabaseRepo::class], version = 2, exportSchema = false)
abstract class RepoDatabase: RoomDatabase() {
    abstract val repoDao: RepoDao
}