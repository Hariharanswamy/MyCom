package com.hariharan.mycom.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariharan.mycom.data.DataRepository
import com.hariharan.mycom.data.model.ProductInfo
import kotlinx.coroutines.launch

/**
 * View model for order fragment
 */
class OrderViewModel : ViewModel() {

    private val orderStatus: MutableLiveData<String>

    private val repository: DataRepository = DataRepository()

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