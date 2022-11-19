package com.example.top_android_repositoriesbs_23.network

import com.squareup.moshi.Json

data class SearchRepositoriesResponse(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Repository>,
)

data class Repository(
    val id: Int,
    val description: String?,
    val name: String?,
    val forks: Int?,
    val language: String?,
    @Json(name = "default_branch")
    val defaultBranch: String?,
    val owner: Owner,
)

data class Owner(
    @Json(name = "avatar_url")
    val avatar: String?,
    val name: String?,
    val login: String?,
    val email: String?,
)

