package com.example.gittrendrepoapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gittrendrepoapp.model.GitRepoListResponse
import com.example.gittrendrepoapp.repository.GitTrendRepository
import com.example.gittrendrepoapp.util.Resource
import com.example.gittrendrepoapp.util.TrendRepoApp
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException

class GitTrendViewModel(app: Application, val gitTrendRepository: GitTrendRepository) :
    AndroidViewModel(app) {

    val gitRepoList: MutableLiveData<Resource<List<GitRepoListResponse>>> = MutableLiveData()
    var gitRepoListResponse: List<GitRepoListResponse>? = null

    init {
        getGitRepoList()
    }

    fun getGitRepoList() = viewModelScope.launch { getList() }

    fun insertAll(gitRepoResponse: List<GitRepoListResponse>) = viewModelScope.launch {
        gitTrendRepository.insertListDb(gitRepoResponse)
    }

    fun savedListOnDb() = gitTrendRepository.getSavedList()

    fun deleteList() = viewModelScope.launch { gitTrendRepository.deleteList() }


    private suspend fun getList() {
        gitRepoList.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = gitTrendRepository.getGitTrendRepoList()
                gitRepoList.postValue(handleList(response))
            } else {
                gitRepoList.postValue(Resource.Error("No connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    gitRepoList.postValue(Resource.Error("Network Failure"))
                }
                is UnknownHostException -> gitRepoList.postValue(Resource.Error("Unknown host!"))
                else -> gitRepoList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleList(response: Response<List<GitRepoListResponse>>): Resource<List<GitRepoListResponse>>? {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                gitRepoListResponse = resultResponse

                return (gitRepoListResponse ?: resultResponse)?.let { Resource.Success(it) }
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<TrendRepoApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false

            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}