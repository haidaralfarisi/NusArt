package com.capstone.nusart.ui_page.login

import androidx.lifecycle.ViewModel
import com.capstone.nusart.data.DataRepository

class LoginViewModel(
    private val repository: DataRepository

) : ViewModel(){
    fun login(email: String, pass: String) = repository.login(email, pass)
}