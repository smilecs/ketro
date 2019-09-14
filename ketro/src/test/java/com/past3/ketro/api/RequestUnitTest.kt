package com.past3.ketro.api

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RequestUnitTest {

    private val request: Request<Unit> = object : Request<Unit>() {
        override suspend fun apiRequest(): Response<Unit> {
            return MockObject.provideUnitResponse()
        }
    }

    @Test
    fun `doRequest should return 200`() {
        runBlocking {
            val data = request.doRequest()
            assert((data.statusCode.code in 200..209))
        }
    }

}