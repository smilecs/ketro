package com.past3.ketro.data.repository

import com.past3.ketro.data.entities.ResponseMapper
import com.past3.ketro.data.repository.datasource.GetUserDataSourceRemote
import com.past3.ketro.domain.entities.Items
import com.past3.ketro.domain.repository.UserRepository
import com.past3.ketro.kcore.model.Wrapper
import com.past3.ketro.kcore.model.mapObject
import javax.inject.Inject

class UserRepositoryimpl @Inject constructor(
        private val userDataSourceRemote: GetUserDataSourceRemote
) : UserRepository {

    override suspend fun getUser(user: String): Wrapper<Items> {
        val resp = userDataSourceRemote.requestGithubUser(user)
        return ResponseMapper().mapObject(resp)
    }

}