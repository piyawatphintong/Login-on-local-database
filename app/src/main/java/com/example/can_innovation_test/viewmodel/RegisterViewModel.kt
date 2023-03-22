package com.example.can_innovation_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.can_innovation_test.roomData.UserDataEntity
import com.example.can_innovation_test.roomData.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    fun insert(user: UserDataEntity) = viewModelScope.launch {
        repository.insert(user)
    }
}