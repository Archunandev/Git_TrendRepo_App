package com.example.gittrendrepoapp.repository


import com.example.gittrendrepoapp.api.RetrofitInstance
import com.example.gittrendrepoapp.db.GitTrendRoomDb
import com.example.gittrendrepoapp.model.GitRepoListResponse

class GitTrendRepository(val db: GitTrendRoomDb) {

    suspend fun getGitTrendRepoList() =
        RetrofitInstance.api.getGitTrendRepo()

    suspend fun insertListDb(gitRepoResponse: List<GitRepoListResponse>) =
        db.gitRepoListDao().insertAll(gitRepoResponse)

    fun getSavedList() = db.gitRepoListDao().getAllGitRepoListFromDb()

    suspend fun deleteList() = db.gitRepoListDao().deleteAll()
}