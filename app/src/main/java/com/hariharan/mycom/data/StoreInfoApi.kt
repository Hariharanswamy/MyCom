package com.hariharan.mycom.data

import com.hariharan.mycom.data.model.StoreInfo
import retrofit2.Response
import retrofit2.http.GET

interface StoreInfoApi {

    @GET("/Hariharanswamy/mycomjson/main/storeInfo.json")
    suspend fun getStoreInfo() : Response<StoreInfo>
}