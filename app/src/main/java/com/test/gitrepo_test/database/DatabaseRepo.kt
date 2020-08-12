package com.test.gitrepo_test.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseRepo(
    @PrimaryKey
    var id: Int,
    var name: String?,
    var full_name: String?,
    var description: String?
)