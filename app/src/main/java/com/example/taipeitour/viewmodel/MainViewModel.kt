package com.example.taipeitour.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitour.model.LanguageModel

class MainViewModel : ViewModel() {
    private val languageModel = LanguageModel()
    private val _languagesLiveData = MutableLiveData<List<String>>()

    val languagesLiveData: LiveData<List<String>> get() = _languagesLiveData

    // 新增 selectedLanguage 變數，並提供 get 方法
    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    init {
        // 初始化語言數據
        _languagesLiveData.value = languageModel.languages.toList()

        // 初始選擇一個語言，這裡可以根據實際需求設定
        _selectedLanguage.value = languageModel.languages.first()
    }

    fun onLanguageSelected(selectedLanguage: String) {
        // 在這裡處理選擇語言的邏輯
        _selectedLanguage.value = selectedLanguage
        //Log.d(ContentValues.TAG, "Selected language: $selectedLanguage")
    }
    // 新增一個方法來獲取當前選擇的語言
    fun getCurrentLanguage(): String {

        return _selectedLanguage.value.takeIf { it?.isNotBlank() == true } ?: "zh-tw"
    }



}