package com.hariharan.mycom.data

import com.hariharan.mycom.data.model.ProductInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {

    @POST("/Hariharanswamy/mycomjson/main/orderApi.json")
    suspend fun uploadProductList(@Body productInfo: List<ProductInfo>): Response<String>
}