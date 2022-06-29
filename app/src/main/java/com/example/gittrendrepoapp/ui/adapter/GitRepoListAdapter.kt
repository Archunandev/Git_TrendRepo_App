package com.example.gittrendrepoapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.gittrendrepoapp.databinding.RepoItemListBinding
import com.example.gittrendrepoapp.model.GitRepoListResponse

class GitRepoListAdapter(private var data: List<GitRepoListResponse>) :
    RecyclerView.Adapter<GitRepoListAdapter.ListViewHolder>(), Filterable {
    var listDataFilter = data

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if (text == null || text.isEmpty()) {
                    filterResults.count = listDataFilter.size
                    filterResults.values = listDataFilter
                } else {
                    var search: String = text.toString().lowercase()
                    var datamodel = ArrayList<GitRepoListResponse>()
                    listDataFilter.forEach {
                        if (it.name!!.lowercase().contains(search)) {
                            datamodel.add(it)
                        }
                    }
                    filterResults.count = datamodel.size
                    filterResults.values = datamodel
                }
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                data = p1!!.values as ArrayList<GitRepoListResponse>
                notifyDataSetChanged()
            }
        }
    }

}