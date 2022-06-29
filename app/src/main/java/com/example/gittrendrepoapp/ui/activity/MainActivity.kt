package com.example.gittrendrepoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendrepoapp.databinding.ActivityMainBinding
import com.example.gittrendrepoapp.repository.GitTrendRepository
import com.example.gittrendrepoapp.util.Resource
import com.example.gittrendrepoapp.viewmodel.GitListViewModelFactory
import com.example.gittrendrepoapp.viewmodel.GitTrendViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding : ActivityMainBinding
    lateinit var viewModel: GitTrendViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        supportActionBar?.elevation = 0F

        val gitTrendRepository = GitTrendRepository()
        val gitListViewModelFactory =GitListViewModelFactory(application, gitTrendRepository)
        viewModel = ViewModelProvider(this, gitListViewModelFactory)[GitTrendViewModel::class.java]

        viewModel.gitRepoList.observe(this, Observer { response ->
            when(response){
                is Resource.Loading -> {Log.e("arjun", "loading " )}
                is Resource.Success -> { Log.e("arjun", "success ${response.data}")}
                is Resource.Error -> { Log.e("arjun", "error ${response.data}")}
            }
        })


    }
}