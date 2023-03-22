package com.example.can_innovation_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.can_innovation_test.roomData.UserDataEntity
import com.example.can_innovation_test.roomData.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) :
    ViewModel() {
    private val _userLiveData = MutableLiveData<UserDataEntity?>()
    val userLiveData: LiveData<UserDataEntity?>
        get() = _userLiveData

    private val _rowCountLiveData = MutableLiveData<Int>()
    val rowCountLiveData: LiveData<Int>
        get() = _rowCountLiveData

    fun checkUserPass(username: String, password: String) = viewModelScope.launch {
        val user =repository.checkUser(username, password)
        _userLiveData.postValue(user)
    }

    fun checkDB() = viewModelScope.launch {
        val count =repository.getRowCount()
        _rowCountLiveData.postValue(count)
    }
}