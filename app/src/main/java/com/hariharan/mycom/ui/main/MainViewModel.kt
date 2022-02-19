package com.hariharan.mycom.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hariharan.mycom.data.DataRepository
import com.hariharan.mycom.data.FileUtil
import com.hariharan.mycom.data.StoreInfo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val storeInfo: MutableLiveData<StoreInfo>
    private val repository: DataRepository

    init {
        val fileUtil = FileUtil(application)
        repository = DataRepository(fileUtil);
        storeInfo = repository.storeInfoLiveData;
    }

    fun getStoreInfoLD(): MutableLiveData<StoreInfo> {
        return storeInfo
    }

    fun getStoreInfo() {
        viewModelScope.launch {
            repository.readStoreInfo()
        }
    }

}