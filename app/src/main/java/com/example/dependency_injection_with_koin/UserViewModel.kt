package com.example.dependency_injection_with_koin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val repository: UserRepository) : ViewModel(), Callback<List<GithubUser>> {
    private val mLoadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = mLoadingState

    private val mData = MutableLiveData<List<GithubUser>>()
    val data: LiveData<List<GithubUser>>
        get() = mData

    init {
        fetchData()
    }

    private fun fetchData() {
        mLoadingState.postValue(LoadingState.LOADING)
        repository.getAllUsers().enqueue(this)
    }

    override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
        mLoadingState.postValue(LoadingState.error(t.message))
    }

    override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
        if (response.isSuccessful) {
            mData.postValue(response.body())
            mLoadingState.postValue(LoadingState.LOADED)
        } else {
            mLoadingState.postValue(LoadingState.error(response.errorBody().toString()))
        }
    }
}