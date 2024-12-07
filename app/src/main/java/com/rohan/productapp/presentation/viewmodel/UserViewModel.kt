package com.rohan.productapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.User
import com.rohan.productapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<ApiResult<User>?>(null)
    val userState: StateFlow<ApiResult<User>?> = _userState

    private val _usersState = MutableStateFlow<ApiResult<List<User>>?>(null)
    val usersState: StateFlow<ApiResult<List<User>>?> = _usersState

    private val _deleteState = MutableStateFlow<ApiResult<Unit>?>(null)
    val deleteState: StateFlow<ApiResult<Unit>?> = _deleteState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    fun getUsers() {
        viewModelScope.launch {
            _loadingState.value = true
            _usersState.value = userUseCase.getUsers()
            _loadingState.value = false
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            _loadingState.value = true
            _userState.value = userUseCase.getUser(userId)
            _loadingState.value = false
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            _loadingState.value = true
            _userState.value = userUseCase.createUser(user)
            _loadingState.value = false
        }
    }

    fun updateUser(id: Int, user: User) {
        viewModelScope.launch {
            _userState.value = userUseCase.updateUser(id, user)
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            _deleteState.value = userUseCase.deleteUser(userId)
        }
    }
}