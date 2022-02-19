package com.hariharan.mycom.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.data.model.ProductInfo
import com.hariharan.mycom.data.model.StoreInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class DataRepository {

    val storeInfoLiveData: MutableLiveData<StoreInfo> = MutableLiveData<StoreInfo>()

    val uploadProductsLiveData: MutableLiveData<String> = MutableLiveData<String>()

    val productInfoLiveData: MutableLiveData<List<ProductInfo>> =
        MutableLiveData<List<ProductInfo>>()

    val baseUrl = "https://raw.githubusercontent.com/"

    private val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()

    suspend fun readStoreInfo() {
        val storeInfoApi = getRetrofitInstance().create(StoreInfoApi::class.java)
        val response = storeInfoApi.getStoreInfo()
        Log.i("info", "Store Response " + response)
        if (response.isSuccessful) {
            storeInfoLiveData.value = response.body();
        }
    }

    suspend fun readProductList() {
        val productListApi = getRetrofitInstance().create(ProductListApi::class.java)
        val response = productListApi.getProductList()
        Log.i("info", "Product Response " + response)
        if (response.isSuccessful) {
            productInfoLiveData.value = response.body();
        }
    }

    suspend fun sendProductList(productInfo: List<ProductInfo>) {
        val productListApi = getRetrofitInstance().create(OrderApi::class.java)
        val response = productListApi.uploadProductList(productInfo)
        Log.i("info", "Product Response " + response)
        uploadProductsLiveData.value = "success"
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}