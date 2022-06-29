package com.example.gittrendrepoapp.api

import com.example.gittrendrepoapp.model.GitRepoListResponse
import retrofit2.Response
import retrofit2.http.GET

interface RepoApi {

    @GET("repositories")
    suspend fun getGitTrendRepo(): Response<List<GitRepoListResponse>>
}