package com.example.myapplication.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    fun settext(test: String) {
        _text.value = test
    }
}