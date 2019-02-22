package com.past3.ketro

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.past3.ketro.model.Wrapper

class MainViewModel: ViewModel(){

    val list = mutableListOf<ResponseModel.Items>()

    private val liveData = MutableLiveData<Wrapper<ResponseModel>>()

    fun searchUser(name:String): LiveData<Wrapper<ResponseModel>> {
        return GitHubRequest(name).doRequest()
    }

    fun searchUserB(name:String) {
        return GitHubRequest(name).executeRequest(liveData)
    }
}