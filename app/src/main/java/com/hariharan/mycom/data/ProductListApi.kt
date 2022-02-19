package com.hariharan.mycom.data

import com.hariharan.mycom.data.model.ProductInfo
import retrofit2.Response
import retrofit2.http.GET

interface ProductListApi {

    @GET("/Hariharanswamy/mycomjson/main/productInfo.json")
    suspend fun getProductList() : Response<List<ProductInfo>>
}