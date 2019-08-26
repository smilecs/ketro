package com.past3.ketro.domain.repository

import com.past3.ketro.domain.entities.Items
import com.past3.ketro.kcore.model.Wrapper

interface UserRepository {

    suspend fun getUser(user:String): Wrapper<Items>

}