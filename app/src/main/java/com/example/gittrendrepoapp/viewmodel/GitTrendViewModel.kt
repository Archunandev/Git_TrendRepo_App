package com.example.gittrendrepoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gittrendrepoapp.model.GitRepoListResponse
import com.example.gittrendrepoapp.repository.GitTrendRepository
import com.example.gittrendrepoapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class GitTrendViewModel(app : Application, val gitTrendRepository: GitTrendRepository) : AndroidViewModel(app) {

    val gitRepoList : MutableLiveData<Resource<List<GitRepoListResponse>>> = MutableLiveData()
    var gitRepoListResponse : List<GitRepoListResponse>? = null

    init {
        getGitRepoList()
    }

    fun getGitRepoList() = viewModelScope.launch {
        getList()
    }

    private suspend fun getList() {
        gitRepoList.postValue(Resource.Loading())
        try {
            val response = gitTrendRepository.getGitTrendRepoList()
            gitRepoList.postValue(handleList(response))
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun handleList(response: Response<List<GitRepoListResponse>>): Resource<List<GitRepoListResponse>>? {
        if (response.isSuccessful){
            response.body().let { resultResponse ->
                gitRepoListResponse = resultResponse

                return (gitRepoListResponse ?: resultResponse)?.let { Resource.Success(it) }
            }
        }
        return Resource.Error(response.message())
    }

}