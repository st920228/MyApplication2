package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text2 = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
    }
    val text2: LiveData<String> = _text2
    fun settext(test: String) {
        _text2.value = test
    }

    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    fun setNext(test: String) {
        _text.value = test
    }
}