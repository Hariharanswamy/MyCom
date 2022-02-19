package com.hariharan.mycom.data.model

import com.google.gson.annotations.SerializedName

data class StoreInfo(

    @SerializedName("name") var name: String,
    @SerializedName("mobile") var mobile: String? = "",
    @SerializedName("address1") var address1: String? = "",
    @SerializedName("address2") var address2: String? = "",
    @SerializedName("city") var city: String? = "",
    @SerializedName("state") var state: String? = "",
    @SerializedName("pin") var pin: Int? = 0,
    @SerializedName("store_id") var storeId: Int,
    @SerializedName("logo") var logo: String? = ""

)