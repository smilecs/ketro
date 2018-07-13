package com.past3.ketro.repo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.past3.ketro.model.Wrapper
import test.smile.lobby.model.GenericVehicleContainer
import test.smile.lobby.model.ResponseWrapper
import test.smile.lobby.repo.api.LobbyRequest

class LobbyViewModel : ViewModel() {

    private val responseLiveData = MutableLiveData<Wrapper<ResponseWrapper>>()
    var isLast: Boolean = false
    val genericList = mutableListOf<GenericVehicleContainer>()
    val wrap = MutableLiveData<Wrapper<String>>()


    fun getManufacturer(page: Int) {
        LobbyRequest(page).executeRequest(responseLiveData)
    }

    fun responseData(): LiveData<Wrapper<List<GenericVehicleContainer>>> {
        return Transformations.map(responseLiveData, {
            val listItem = it.data?.let {
                isLast = (it.page == it.totalPageCount - 1) || (it.page == it.totalPageCount)
                it.wkda.flatMap {
                    listOf(GenericVehicleContainer(it.key, it.value))
                }
            }
            Wrapper<List<GenericVehicleContainer>>().apply {
                data = listItem
            }
        })
    }
}