package com.hariharan.mycom.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

class DataRepository(private val fileUtil: FileUtil) {

    val storeInfoLiveData : MutableLiveData<StoreInfo> = MutableLiveData<StoreInfo>()

    suspend fun readStoreInfo() {
        val jsonStr: String = fileUtil.readAssetFile("storeInfo.json")
        val gson: Gson = Gson()
        val storeInfo : StoreInfo = gson.fromJson(jsonStr, StoreInfo::class.java)
        storeInfoLiveData.value = storeInfo;
    }

}