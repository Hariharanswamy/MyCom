package com.hariharan.mycom.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hariharan.mycom.data.DataRepository
import com.hariharan.mycom.data.model.ProductInfo
import kotlinx.coroutines.launch

/**
 * View model for order fragment
 */
class OrderViewModel(application: Application) : AndroidViewModel(application){

    private val orderStatus: MutableLiveData<String>

    private val repository: DataRepository = DataRepository(application.applicationContext)

    init {
        orderStatus = repository.uploadProductsLiveData
    }

    fun getOrderSummaryLD(): MutableLiveData<String> {
        return orderStatus
    }

    fun sendProductList(productInfo: List<ProductInfo>) {
        viewModelScope.launch {
            repository.sendProductList(productInfo)
        }
    }
}