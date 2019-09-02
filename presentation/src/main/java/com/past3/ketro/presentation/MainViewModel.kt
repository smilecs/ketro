package com.past3.ketro.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.past3.ketro.domain.GetUserUseCase
import com.past3.ketro.domain.entities.Items
import com.past3.ketro.kcore.model.Wrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val list = mutableListOf<Items>()

    private val liveData = MutableLiveData<Wrapper<Items>>()

    val _liveData: LiveData<Wrapper<Items>> = liveData

    private val viewModelJob = SupervisorJob()

    private val scope = CoroutineScope(Dispatchers.Default
            + viewModelJob)

    fun searchUser(name: String) {
        scope.launch {
            val user = getUserUseCase(name)
            withContext(Dispatchers.Main) {
                liveData.value = user
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}