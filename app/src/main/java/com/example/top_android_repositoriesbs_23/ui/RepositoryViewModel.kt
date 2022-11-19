package com.example.top_android_repositoriesbs_23.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top_android_repositoriesbs_23.network.Repository
import com.example.top_android_repositoriesbs_23.network.RepositoryApi

import kotlinx.coroutines.launch


enum class ApiStatus { LOADING, ERROR, DONE }

class RepositoryViewModel : ViewModel() {

    var selectedRepository: Repository? = null
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _repositories.value = RepositoryApi.retrofitService.searchRepositories(query, 20, 1).items
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _repositories.value = listOf()
            }
        }
    }
}