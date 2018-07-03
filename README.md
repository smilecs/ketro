# ketro
This is a Retrofit response wrapper written in Kotlin that can be used to easily wrap REST API response to LiveData and exception/error
handling both from the retrofit calls all the way to displaying an error in the view. Ketro allows and encourages the addition of custom exceptions
so errors can easily be grouped and managed with adequate actions and feedback to your app users.

## Include Dependency
Currently Ketro is hosted on MavenCentral, just add 
```
implementation 'past3.smilecs:ketro:1.0.3'
```
to your project gradle file then open your root gradle file and add the below lines
```
maven {
            url  "https://dl.bintray.com/smilecs/ketro"
        }
```         

## Ketro Request methods
Ketro offers a selection of methods that wrap your retrofit calls and return a LiveData object with a wrapper that contains an exception object if the request was unsuccessful or as the user defines.
These methods are:
- `doRequest() : LiveData<Wrapper<R>>`
- `executeRequest(liveData:MutableLiveData<Wrapper<R>>)`
## Usage
Inorder to use these wrappers for your request, you must extend the ketro `GenericRequestHandler<T>` which takes in a class type which you would like to observe with livedata.

``` 
class LobbyRequest(private val mainType: String = "") : GenericRequestHandler<ResponseWrapper>() {
    private val lobbyService: LobbyService by lazy {
        NetModule.provideRetrofit().create(LobbyService::class.java)
    }

    override fun makeRequest(): Call<ResponseWrapper> {
        //Retrofit interface method
        return lobbyService.getManufacturers()
        }
    }
} 
```
* Note put in your retrofit request call into the `makeRequest()` method.
##### After creating your request handler as above,
To make the actual api call, create an object of the request class and call the `doRequest()`.

```
/...../
fun getManufacturer() {
        LobbyRequest(LobbyRequest.MANUFACTURER).doRequest().observe(this, object : Kobserver<GenericVehicleContainer>() {
            override fun onException(exception: Exception) {
                //handle exceptions here, custom exception types inclusive
            }

            override fun onSuccess(data: GenericVehicleContainer) {
                
            }
        })
    }
/....../    
```
As noted above the Request class `doRequest()` executes the api call and returns an observable live data object. Now Ketro offers an extension of the Android Observer class(Kobserver), which attempts to handle api errors/exceptions delegated by the user, hence why we have an exception and a success callback.
* Note Using the Kobserver with the returned api response is optional, but recommended for proper error handling.

There are situations where you may want to have a separate request method and a separate LiveData object update when the request resolves. In these kind of scenarios
instead of calling `doRequest()` we would call `executeRequest(liveData: MutableLiveData<Wrapper<R>>)` this method needs the specificed response type to be wrapped with a Ketro Wrapper class so it can propagate errors effectively. Internally all the methods wrap each object with the Ketro Wrapper.

```
val wrap = MutableLiveData<Wrapper<VehicleContainer>>()
fun getManufacturer() {
     LobbyRequest(LobbyRequest.MANUFACTURER).executeRequest(responseLiveData)
}
```
After the request is resolved, the `LiveData` object passed in, has it's value set with the response and all active observers of the `LiveData` are triggered.

.......<b>More info on passing custom error exception handling coming soon</b>
### Also for any request or anything unclear with the library feel free to hit me up, on mumene@gmail.com or create an issue ticket.
