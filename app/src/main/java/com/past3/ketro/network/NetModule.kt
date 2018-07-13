package com.past3.ketro.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

/**
 * Created by mmumene on 03/09/2017.
 */

object NetModule {

    private fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
        client.addNetworkInterceptor(CacheInterceptor())
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.BASE_URL)
                .client(provideOkhttpClient())
                .build()
    }

    private class  CacheInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain?): Response {
            val response = if (chain?.request()?.header("Cache-Control") != null) {
                        Response.Builder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "max-age=2400")
                        .build()
            }else{
                chain?.proceed(chain.request())
            }
            return response!!
        }
    }

    private class NullOnEmptyConverterFactory : Converter.Factory() {

        override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, Any>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter{ body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
        }
    }
}
