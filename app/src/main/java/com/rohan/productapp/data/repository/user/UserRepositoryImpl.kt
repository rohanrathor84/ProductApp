package com.rohan.productapp.data.repository.user

import com.rohan.productapp.data.remote.UserApiService
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.di.safeApiCall
import com.rohan.productapp.domain.model.User

class UserRepositoryImpl(private val userApiService: UserApiService): UserRepository {
    override suspend fun getUsers(): ApiResult<List<User>> {
        return safeApiCall { userApiService.getUsers() }
    }

    override suspend fun getUser(id: Int): ApiResult<User> {
        return safeApiCall { userApiService.getUser(id) }
    }

    override suspend fun createUser(user: User): ApiResult<User> {
        return safeApiCall { userApiService.createUser(user) }
    }

    override suspend fun updateUser(id: Int, user: User): ApiResult<User> {
        return safeApiCall { userApiService.updateUser(id, user) }
    }

    override suspend fun deleteUser(id: Int): ApiResult<Unit> {
        return safeApiCall { userApiService.deleteUser(id) }
    }
}