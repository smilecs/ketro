package com.past3.ketro.api

import retrofit2.Response

object MockObject {
    fun provideUnitResponse(): Response<Unit> {
        return Response.success(200, Unit)
    }
}