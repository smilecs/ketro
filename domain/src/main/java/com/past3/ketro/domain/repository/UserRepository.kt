package com.past3.ketro.domain.repository

import com.past3.ketro.domain.entities.Items
import com.past3.ketro.kcore.model.KResponse

interface UserRepository {

    suspend fun getUser(user: String): KResponse<Items>

}