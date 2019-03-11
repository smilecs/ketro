# ketro

![Alt text](ketro.png?raw=true "Title")

Ketro is a Retrofit response wrapper written in Kotlin that can be used to easily wrap REST API response to LiveData and exception/error
handling both from the retrofit calls all the way to displaying an error in the view. Ketro allows and encourages the addition of custom exceptions
so errors can easily be grouped and managed with adequate actions and feedback to your app users.

## Include Dependency
Currently Ketro is hosted on Jcenter, just add the below line to your app gradle file
```groovy
implementation 'past3.smilecs.ketro:ketro:1.1.1'
```

## Ketro Request methods
Ketro offers a selection of methods that wrap your retrofit calls and return a LiveData object with a wrapper that contains an exception object if the request was unsuccessful or as the user defines.
These methods are:
- `doRequest() : LiveData<Wrapper<R>>`
- `executeRequest(liveData:MutableLiveData<Wrapper<R>>)`
## Usage
Inorder to use these wrappers for your request, you must extend the ketro `GenericRequestHandler<T>` which takes in a class type which you would like to observe with livedata.

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
* Note put in your retrofit request call into the `makeRequest()` method.
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
As noted above the Request class `doRequest()` executes the api call and returns an observable live data object. Now Ketro offers an extension of the Android Observer class(Kobserver), which attempts to handle api errors/exceptions delegated by the user, hence why we have an exception and a success callback.
* Note Using the Kobserver with the returned api response is optional, but recommended for proper error handling.

There are situations where you may want to have a separate request method and a separate LiveData object update when the request resolves. In these kind of scenarios,
instead of calling `doRequest()`, we would call `executeRequest(liveData: MutableLiveData<Wrapper<R>>)`. This method needs the specified response type to be wrapped with the `Wrapper` class in Ketro so it can propagate errors effectively. Internally all the methods wrap each object with the Ketro Wrapper.

```kotlin
val wrap = MutableLiveData<Wrapper<VehicleContainer>>()
fun getManufacturer() {
     LobbyRequest(LobbyRequest.MANUFACTURER).executeRequest(responseLiveData)
}
```
After the request is resolved, the `LiveData` object passed in, has it's value set with the response and all active observers of the `LiveData` are triggered.

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


After creating your class and modifiying your request handler you can go ahead to check for the exception in your View(Activity/Fragment)
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
Passing in a error handling function will allow you to omit the `onException` callback which is
now optional, note that passing in a function will stop the `onException` callback from executing
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
