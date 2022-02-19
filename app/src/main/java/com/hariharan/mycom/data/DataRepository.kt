package com.hariharan.mycom.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataRepository(private val fileUtil: FileUtil) {

    val storeInfoLiveData : MutableLiveData<StoreInfo> = MutableLiveData<StoreInfo>()

    val productInfoLiveData : MutableLiveData<List<ProductInfo>> = MutableLiveData<List<ProductInfo>>()

    private val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()

    suspend fun readStoreInfo() {
        val jsonStr: String = fileUtil.readAssetFile("storeInfo.json")
        val gson: Gson = Gson()
        val storeInfo : StoreInfo = gson.fromJson(jsonStr, StoreInfo::class.java)
        storeInfoLiveData.value = storeInfo;
    }

    suspend fun readProductList() {
        val jsonStr: String = fileUtil.readAssetFile("productInfo.json")
        val gson: Gson = Gson()
        val productInfo : List<ProductInfo> = gson.fromJson(jsonStr, listType)
        productInfoLiveData.value = productInfo;
    }

}