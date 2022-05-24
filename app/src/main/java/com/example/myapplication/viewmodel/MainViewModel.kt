package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.User
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val data: MutableLiveData<List<User>> = MutableLiveData()

    fun requestRepository(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUsers(name).let {
                if (it.isSuccessful) {
                    it.body()?.let { users ->
                        data.postValue(users.items)
                    }
                }
            }
        }
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(userRepository) as T
        }
    }
}