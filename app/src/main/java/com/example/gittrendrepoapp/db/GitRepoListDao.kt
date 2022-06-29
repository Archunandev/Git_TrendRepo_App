package com.example.gittrendrepoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gittrendrepoapp.model.GitRepoListResponse


@Dao
interface GitRepoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(data: List<GitRepoListResponse>)

    @Query("SELECT * FROM gitRepoList")
    fun getAllGitRepoListFromDb(): LiveData<List<GitRepoListResponse>>

    @Query("DELETE FROM gitRepoList")
    suspend fun deleteAll(): Int
}