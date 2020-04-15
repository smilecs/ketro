package com.past3.ketro.domain

import com.past3.ketro.domain.entities.Items
import com.past3.ketro.domain.repository.UserRepository
import com.past3.ketro.kcore.model.KResponse
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
        private val userRepository: UserRepository
) {

    suspend operator fun invoke(user: String): KResponse<Items> {
        return userRepository.getUser(user)
    }

}