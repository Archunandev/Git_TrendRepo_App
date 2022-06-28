package com.example.gittrendrepoapp.model

import com.google.gson.annotations.SerializedName

data class GitRepoListResponse(
    @SerializedName("name") var name: String?= null,
    @SerializedName("description") var description: String?= null,
    @SerializedName("language") var language: String?= null,
    @SerializedName("languageColor") var languageColor: String?= null,
    @SerializedName("stars") var stars: Int?= null
)
