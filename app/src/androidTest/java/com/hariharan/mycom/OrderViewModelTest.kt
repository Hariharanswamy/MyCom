package com.hariharan.mycom

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.data.model.ProductInfo
import com.hariharan.mycom.ui.order.OrderViewModel
import org.awaitility.Awaitility
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Test class for Order view model class
 */
@RunWith(AndroidJUnit4::class)
public class OrderViewModelTest {

    @get:Rule
    public val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewmodel: OrderViewModel

    private lateinit var response: String

    @Before
    fun setUp() {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        viewmodel = OrderViewModel(application as Application)
    }

    @Test
    public fun testSendProductList() {
        viewmodel.getOrderSummaryLD().observeForever { string ->
            response = string
        }
        viewmodel.sendProductList(getProductInfoResponse())
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(Callable { response != null })
        Assert.assertEquals("success", response)
    }


    private fun getProductInfoResponse(): List<ProductInfo> {
        val inputStream =
            InstrumentationRegistry.getInstrumentation().targetContext.assets.open("productInfo.json")
        val bytes = inputStream.readBytes()
        val text = String(bytes, StandardCharsets.UTF_8)
        inputStream.close()
        val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()
        return Gson().fromJson(text, listType)
    }
}