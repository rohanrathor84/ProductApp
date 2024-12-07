package com.rohan.productapp.data.repository.user

import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.User

interface UserRepository {
    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUser(id: Int): ApiResult<User>
    suspend fun createUser(user: User): ApiResult<User>
    suspend fun updateUser(id: Int, user: User): ApiResult<User>
    suspend fun deleteUser(id: Int): ApiResult<Unit>
}