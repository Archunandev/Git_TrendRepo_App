package com.example.gittrendrepoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "gitRepoList"
)
data class GitRepoListResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var language: String? = null,
    var languageColor: String? = null,
    var stars: Int? = null
) : Serializable
