package com.example.gittrendrepoapp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gittrendrepoapp.databinding.RepoItemListBinding
import com.example.gittrendrepoapp.model.GitRepoListResponse

class GitRepoListAdapter(private var data: List<GitRepoListResponse>) :
    RecyclerView.Adapter<GitRepoListAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: RepoItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            RepoItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var repoList = data[position]
        holder.binding.apply {
            nameTxt.text = repoList.name
            description.text = repoList.description
            languageTxt.text = repoList.language
            languageColorCode.setBackgroundColor(Color.parseColor("${repoList.languageColor}"))
            starsNumber.text = repoList.stars.toString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}