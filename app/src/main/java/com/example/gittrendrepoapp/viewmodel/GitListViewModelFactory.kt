package com.example.gittrendrepoapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendrepoapp.repository.GitTrendRepository

class GitListViewModelFactory(val app : Application, val gitTrendRepository: GitTrendRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitTrendViewModel(app, gitTrendRepository) as T
    }
}