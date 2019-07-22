package com.past3.ketro

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.past3.ketro.model.Wrapper
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val list = mutableListOf<ResponseModel.Items>()

    private val liveData = MutableLiveData<Wrapper<ResponseModel>>()

    val _liveData: LiveData<Wrapper<ResponseModel>> = liveData

    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun searchUser(name: String): LiveData<Wrapper<ResponseModel>> {
        return GitHubRequest(name).doRequest()
    }

    fun searchUserB(name: String) {
        return GitHubRequest(name).executeRequest(liveData)
    }

    fun getGitHubUser() {
        uiScope.launch {
            val wrapper = CoRountineSampleRequest().requestGithubUser()
            liveData.value = wrapper
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}