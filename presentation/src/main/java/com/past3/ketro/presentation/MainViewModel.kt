package com.past3.ketro.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.past3.ketro.api.LiveDataHandler
import com.past3.ketro.domain.GetUserUseCase
import com.past3.ketro.domain.entities.Items
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val list = mutableListOf<Items>()

    private val _errorLiveData: MutableLiveData<Exception> = MutableLiveData()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val liveDataHandler = LiveDataHandler(_errorLiveData)

    private val liveData = MutableLiveData<Items>()

    val _liveData: LiveData<Items> = liveData

    private val viewModelJob = SupervisorJob()

    private val scope = CoroutineScope(Dispatchers.Default
            + viewModelJob)

    fun searchUser(name: String) {
        scope.launch(handler()) {
            val user = getUserUseCase(name)
            withContext(Dispatchers.Main) {
                liveDataHandler.emit(user, liveData)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}