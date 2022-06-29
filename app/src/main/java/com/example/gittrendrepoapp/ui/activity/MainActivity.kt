package com.example.gittrendrepoapp.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendrepoapp.R
import com.example.gittrendrepoapp.databinding.ActivityMainBinding
import com.example.gittrendrepoapp.db.GitTrendRoomDb
import com.example.gittrendrepoapp.repository.GitTrendRepository
import com.example.gittrendrepoapp.ui.adapter.GitRepoListAdapter
import com.example.gittrendrepoapp.util.Resource
import com.example.gittrendrepoapp.util.Utility.isVisibility
import com.example.gittrendrepoapp.viewmodel.GitListViewModelFactory
import com.example.gittrendrepoapp.viewmodel.GitTrendViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: GitTrendViewModel
    private var gitRepoListAdapter: GitRepoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        supportActionBar?.elevation = 0F

        val gitTrendRepository = GitTrendRepository(GitTrendRoomDb(this))
        val gitListViewModelFactory = GitListViewModelFactory(application, gitTrendRepository)
        viewModel = ViewModelProvider(this, gitListViewModelFactory)[GitTrendViewModel::class.java]

        activityMainBinding.apply {
            pullToRefresh.setOnRefreshListener { getRepoList() }
            tryAgain.setOnClickListener { getRepoList() }
        }
        getRepoList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu?.findItem(R.id.search_action)
        val searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        return true
    }

    private fun getRepoList() {
        viewModel.gitRepoList.observe(this, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    activityMainBinding.progress.isVisibility(true)
                }
                is Resource.Success -> {
                    gitRepoListAdapter = response.data?.let { GitRepoListAdapter(it) }
                    viewModel.deleteList()
                    response.data?.let { viewModel.insertAll(it) }
                    activityMainBinding.apply {
                        gitrepoRecy.adapter = gitRepoListAdapter
                        progress.isVisibility(false)
                        pullToRefresh.isRefreshing = false
                    }
                }
                is Resource.Error -> {
                    getFromDb()
                    activityMainBinding.apply {
                        progress.isVisibility(false)
                        networkError.isVisibility(true)
                    }
                }
            }
        })
    }

    private fun getFromDb() {
        viewModel.savedListOnDb().observe(this, Observer { repoList ->
            Log.e("arjun", "getRepoListDb:${repoList.toList().size}")
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        try {
            gitRepoListAdapter!!.filter.filter(newText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }
}
