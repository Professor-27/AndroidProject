package com.example.AniMistry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    // LiveData to observe
    private val _exampleData = MutableLiveData<String>()
    val exampleData: LiveData<String> get() = _exampleData

    init {
        // Initialize with some data or perform setup
        _exampleData.value = "Hello, ViewModel!"
    }

    // Add methods to update LiveData or perform business logic
    fun updateData(newData: String) {
        _exampleData.value = newData
    }
}