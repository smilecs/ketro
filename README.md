# ketro

![Alt text](ketro.png?raw=true "Title")

Ketro is a Retrofit response wrapper written in Kotlin that can be used to easily wrap REST API response to LiveData and exception/error
handling both from the retrofit calls all the way to displaying an error in the view. Ketro allows and encourages the addition of custom exceptions
so errors can easily be grouped and managed with adequate actions and feedback to your app users.

## Include Dependency

Ketro is hosted using JitPack, just add the below line to your root `build.gradle` file

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
## Multi - module projects:
Ketro now supports multi-module projects, the Ketro modules such as Wrapper and ApiErrorHandler have been put into a separate package
to allow you expose these in a domain layer without including the Ketro dependency so as to enable separation of concerns between the
data, presentation and domain layer.

```groovy
dependencies {
    implementation 'com.github.smilecs:ketro:1.4'
}
```

## Ketro Request methods

Ketro offers a selection of methods that wrap your retrofit calls and return a LiveData object with a wrapper that contains an exception object if the request was unsuccessful or as the user defines.
These methods are:

- `doRequest() : LiveData<Wrapper<R>>`
- `executeRequest(liveData:MutableLiveData<Wrapper<R>>)`
- `suspend fun doRequest(): Wrapper<T>`
- `suspend fun execute(): KResponse<T>`

## Usage

Inorder to use these wrappers for your request, you must extend the ketro `GenericRequestHandler<T>`  which takes in a class type which you would like to observe with livedata.
Ketro offers two request patterns:

1. `GenericRequestHandler<T>`
2. `Request<T>`

### 1. GenericRequestHandler API

```kotlin
class LobbyRequest(private val mainType: String = "") : GenericRequestHandler<VehicleContainer>() {
    private val lobbyService: LobbyService by lazy {
        NetModule.provideRetrofit().create(LobbyService::class.java)
    }

    override fun makeRequest(): Call<VehicleContainer> {
        //Retrofit interface method
        return lobbyService.getManufacturers()
        }
    }
```

- Note put in your retrofit request call into the `makeRequest()` method.

##### After creating your request handler as above,

To make the actual api call, create an object of the request class and call the `doRequest()`.

```kotlin
fun getManufacturer() {
        LobbyRequest(LobbyRequest.MANUFACTURER).doRequest().observe(this, object : Kobserver<VehicleContainer>() {
            override fun onException(exception: Exception) {
                //handle exceptions here, custom exception types inclusive
            }

            override fun onSuccess(data: VehicleContainer) {

            }
        })
    }
```

```kotlin
viewModel._liveData.observe(this, object : Kobserver<ResponseModel>() {
            override fun onSuccess(data: ResponseModel) {
                Toast.makeText(this@MainActivity, "Works", Toast.LENGTH_LONG).show()
            }

            override fun onException(exception: Exception) {
                userErrorHanlder(exception)
            }
        })

 private fun userErrorHanlder(ex: Exception) {
        when (ex) {
            is GitHubErrorHandler.ErrorConfig.NetworkException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            is GitHubErrorHandler.ErrorConfig.GitHubException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this@MainActivity, "Oops! Something went wrong.", Toast.LENGTH_LONG).show()
        }
    }
```

As noted above the Request class `doRequest()` executes the api call and depending on usage it could either 
return an observable live data object or a data wrapper of type `Wrapper<T>` were `T` represents your object, within this wrapper class
you have access to your data or exceptions as well as Status code. 
Now Ketro offers an extension of the Android Observer class(`Kobserver<T>`), which attempts to handle api errors/exceptions delegated by the user, 
hence why we have an exception and a success callback.

- Note Using the Kobserver with the returned api response is optional, but recommended for proper error handling.

There are situations where you may want to have a separate request method and a separate LiveData object update when the request resolves. In such scenarios,
instead of calling `doRequest()`, we would call `executeRequest(liveData: MutableLiveData<Wrapper<R>>)` or use the Coroutines helper
as described in the next section. 
This method needs the specified response type to be wrapped with the `Wrapper` class in Ketro so it can propagate errors effectively. 
Internally all the methods wrap each object with the Ketro Wrapper.

```kotlin
val wrap = MutableLiveData<Wrapper<VehicleContainer>>()
fun getManufacturer() {
     LobbyRequest(LobbyRequest.MANUFACTURER).executeRequest(responseLiveData)
}
```

After the request is resolved, the `LiveData` object passed in will have its value set with the response and all active observers of the `LiveData` are triggered.

### 2. Request API Implementation with Coroutines helper

This sections shows examples on how to use the `Request API`, the samples provided will come in pairs, one would be for 
responses using the `execute -> KResponse<T>` and the other would be for `doRequest -> Wrapper<T>`.

##### Note: The request api uses Coroutine Suspend functions. 

Create your class holding your network requests or you can use one class per request, the `doRequest():Wrapper<T>` suspention method from 
the `Request` class is used to make the network call:

```kotlin
class CoRoutineSampleRequest {
    private val gitHubAPI: GitHubAPI by lazy {
        NetworkModule.createRetrofit().create(GitHubAPI::class.java)
    }

     suspend fun requestGithubUser(user: String): Wrapper<ResponseItems> {
            val req = object : Request<ResponseItems>(GitHubErrorHandler()) {
                override suspend fun apiRequest(): Response<ResponseItems> =
                        gitHubAPI.searchUse(user)
            }
            return req.doRequest()
        }

}
```

Example using `Request API`
```kotlin
class GetUserDataSourceRemote @Inject constructor(private val gitHubAPI: GitHubAPI) {

    suspend fun requestGithubUser(user: String): KResponse<ResponseItems> {
        val req = object : Request<ResponseItems>(GitHubErrorHandler()) {
            override suspend fun apiRequest(): Response<ResponseItems> =
                    gitHubAPI.searchUse(user)
        }
        return req.execute()
    }

}
```

Override the errorHandler class for adding extra/custom ErrorHandling exceptions details in next section Error Handling.

##### Note:
The `doRequest` method returns a `Wrapper` with the response encapsulated within it and returns the error/error code and custom exceptions
as you may have defined.

```kotlin
private val viewModelJob = SupervisorJob()

//Scope coroutine to a ViewModel or use global scope
private val scope = CoroutineScope(Dispatchers.Default + viewModelJob)

fun getGitHubUser() {
     scope.launch {
         val user = getUserUseCase(name)
          withContext(Dispatchers.Main) {
          liveData.value = user
           }
        }
    }
```

```kotlin
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
```
Above is an example of handling data response of type KResponse in the ViewModel and emitting either a failure or the success `T`
The `LiveDataHandler` is a function within ketro that parses the KResponse return object and can emit either the success or failure object.

```kotlin
private val _errorLiveData: MutableLiveData<Exception> = MutableLiveData()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val liveDataHandler = LiveDataHandler(_errorLiveData)
```
To use the LiveDataHandler initialise it with a LiveData that takes in an exception i.e `LiveData<Exception>` this
is because the KResponse wraps errors in exceptions that can be predefined by the user Using the `ApiErrorHandler` which allows you to OverRide and add your own error
definitions. Then on the view you can collect your LiveData values as normal because if success the `LiveDataHandler` will
emit the success value to the specific `LiveData`.

```kotlin
   viewModel._liveData.observe(this, Observer {
            toggleViews(true)
            Toast.makeText(this@MainActivity, "Works", Toast.LENGTH_LONG).show()
        })

        viewModel.errorLiveData.observe(this, Observer { ex ->
            ex?.let {
                userErrorHanlder(it)
            }
        })
```


## Error Handling

Handling custom errors with Ketro is quite simple, the library expects you use either the response code gotten from your server or a custom message gotten from your server and map an Exception to which would be return to the request class by overriding the error handler object to return your class with your error mapping implementation.
Note if this is not provided, a default exception is returned and propaged to the views callback interface.
First off you need to create a class which extends `ApiErrorHandler` then you can either put your own Exception cases there or create a new class for each exception case depends on your preference.
Ketro now provides a Kexception class which return the Respoonse Error Body, though the getExceptionType returns the exception
super type, so as to make using the Kexception class optional.

```kotlin
import com.past3.ketro.api.ApiErrorHandler
import retrofit2.Response

class LobbyErrorHandler : ApiErrorHandler() {

    override fun getExceptionType(response: Response<*>): Exception {
        return when (response.code()) {
            LOGIN_ERROR -> LoginException(response.errorBody(), response.message())
            UPDATE_ERROR -> UpdateException()
            else -> Exception()
        }
    }

    companion object ErrorConfig {
        const val LOGIN_ERROR = 401
        const val UPDATE_ERROR = 404
        //sub-classing kexception allows you to have access to the errorbody
        class LoginException(val errorBody: ResponseBody?, message: String?, cause: Throwable? = null)
         : Kexception(message, cause) {
            override val message = "Error processing login details"
        }

        class UpdateException : Exception() {
            override val message = "Error updating details"
        }
    }

}
```

Now you can choose to map your errors anyway you like to an exception, for me I prefer to use http error status codes to determine what kind of exception I return to the Wrapper object you can as well choose to return an error object from your server and map that out to your exception, the possibilities are endless.

Also, remember the request class you created earlier? you will need to override the `ApiErrorhandler` field and initialise your custom class, the rest will be handled by Ketro.

```kotlin
class LobbyRequest(private val page: Int) : GenericRequestHandler<VehicleContainer>() {

    private val lobbyService: LobbyService by lazy {
        NetModule.provideRetrofit().create(LobbyService::class.java)
    }

    override val errorHandler: ApiErrorHandler = LobbyErrorHandler()

    override fun makeRequest(): Call<VehicleContainer> {
        return lobbyService.getManufacturers(page, pageSize, Urls.KEY)
    }
}
```

After creating your class and modifying your request handler you can go ahead to check for the exception in your View(Activity/Fragment)
Here you can Also check if the exception is of type Kexception and use the errorBody included within the object.
Note: the onException override is now optional, and as well you can pass in a function into the kobserver
constructor to handle your errors and as well get a cleaner interface

```kotlin
viewModel.responseData().observe(this, object : Kobserver<List<VehicleContainer>>() {
            override fun onException(exception: Exception) {
             when (exception) {
                is Kexception -> {
                    //exception.errorBody do something with errorBody
                 }
                 is LobbyErrorHandler.ErrorConfig.UpdateException ->{
                    // handle error e.g. show dialog, redirect user etc.
                 }
             }
            }

            override fun onSuccess(data: List<VehicleContainer>) {
                // Update UI
                swipeRefresh.isRefreshing = false
                searchView.visibility = View.VISIBLE
                helperContainer.visibility = View.VISIBLE

                viewModel.genericList.addAll(data)
                vehicleAdapter.notifyDataSetChanged()
                searchView.setAdapter(searchAdapter)
                searchAdapter.notifyDataSetChanged()

            }
        })
```

### Alternatively

Passing in an error handling function will allow you to omit the `onException` callback which is
now optional when using the `Kobserver`.
##### Note: that passing in a function will stop the `onException` callback from executing
so it's a choice between using either one.

```kotlin

private fun userErrorHanlder(ex: Exception) {
        //handle errors here
}

 viewModel.searchUser(editText.text.toString()).observe(this, object : Kobserver<ResponseModel>(::userErrorHanlder) {
            override fun onSuccess(data: ResponseModel) {
                if (data.items.isEmpty()) {
                    toggleViews(false)
                    return
                }
                toggleViews(true)
                viewModel.list.let {
                    it.clear()
                    it.addAll(data.items)
                    listAdapter.submitList(it)
                }
            }

        })
```

### Also for any request or anything unclear with the library feel free to hit me up, on mumene@gmail.com or create an issue ticket.

