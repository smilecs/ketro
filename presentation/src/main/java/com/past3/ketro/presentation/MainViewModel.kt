package com.past3.ketro.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val scope = CoroutineScope(
        Dispatchers.Default
                + viewModelJob
    )

    fun searchUser(name: String) {
        scope.launch(handler()) {
            val user = getUserUseCase(name)
            withContext(Dispatchers.Main) {
                liveDataHandler.emit(user, liveData)
            }
        }
    }

    private fun handler(action: (() -> Any)? = null): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is IOException -> {
                    action?.invoke()
                }
                else -> {
                    throwable.printStackTrace()
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}