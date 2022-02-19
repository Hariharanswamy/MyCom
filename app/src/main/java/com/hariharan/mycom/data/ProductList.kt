package com.hariharan.mycom.data

import com.google.gson.annotations.SerializedName

data class ProductList(

    @SerializedName("name") var name: String? = "",
    @SerializedName("photo_url") var photoUrl: String? = "",
    @SerializedName("quantity") var quantity: String? = "",
    @SerializedName("id") var id: Int? = -1,
    @SerializedName("price") var price: Int? = 0

)