package com.hariharan.mycom.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.data.model.ProductInfo
import com.hariharan.mycom.data.model.StoreInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.lang.reflect.Type

class DataRepository(private val context: Context) {

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
        try {
            val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()
            val gson = Gson().toJson(productInfo, listType )
            val file = File(context.filesDir, "OrderSummary.json")
            file.createNewFile()
            file.appendText(gson)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        uploadProductsLiveData.value = "success"
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}