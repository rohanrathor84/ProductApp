package com.rohan.productapp.domain.usecase

import com.rohan.productapp.data.repository.user.UserRepository
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.User
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun getUsers(): ApiResult<List<User>> {
        return userRepository.getUsers()
    }

    suspend fun getUser(userId: Int): ApiResult<User> {
        return userRepository.getUser(userId)
    }

    suspend fun createUser(user: User): ApiResult<User> {
        return userRepository.createUser(user)
    }

    suspend fun updateUser(id: Int, user: User): ApiResult<User> {
        return userRepository.updateUser(id, user)
    }

    suspend fun deleteUser(userId: Int): ApiResult<Unit> {
        return userRepository.deleteUser(userId)
    }
}