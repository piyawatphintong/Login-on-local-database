package com.example.can_innovation_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.can_innovation_test.roomData.UserDataEntity
import com.example.can_innovation_test.roomData.UserRepository
import kotlinx.coroutines.launch

class MainPageViewModel(private val repository: UserRepository) : ViewModel() {
    private lateinit var userData: UserDataEntity
    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email
    private var _dob = MutableLiveData<String>()
    val dob: LiveData<String>
        get() = _dob
    private var _phoneNum = MutableLiveData<String>()
    val phoneNum: LiveData<String>
        get() = _phoneNum

    fun getUserData(username: String) = viewModelScope.launch {
        userData = repository.getUserByUsername(username)!!
        updateData(
            fullName = username,
            Email = userData.Email,
            Dob = userData.BirthDate,
            PhoneNum = userData.TelephoneNumber
        )
    }

    private fun updateData(fullName: String, Email: String, Dob: String, PhoneNum: String) {
        _name.value = fullName
        _email.value = Email
        _dob.value = Dob
        _phoneNum.value = PhoneNum
    }
}