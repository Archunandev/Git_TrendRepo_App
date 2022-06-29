package com.example.gittrendrepoapp.repository

import com.example.gittrendrepoapp.api.RetrofitInstance

class GitTrendRepository {

    suspend fun getGitTrendRepoList() =
        RetrofitInstance.api.getGitTrendRepo()
}