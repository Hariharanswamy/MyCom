package com.hariharan.mycom

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.hariharan.mycom.ui.main.MainViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility.await
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Test cases for Main View Model class
 */
@RunWith(MockitoJUnitRunner::class)
public class MainViewModelTest {

    private var responsecode = -1;

    private lateinit var response: String

    private lateinit var viewmodel: MainViewModel

    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    public val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        viewmodel = MainViewModel(application as Application)
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun clear() {
        mockWebServer.shutdown()
        responsecode = -1
    }

    @Test
    public fun testStoreInfo() {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getStoreInfoResponse())
        mockWebServer.enqueue(mockResponse)
        viewmodel.getStoreInfoLD().observeForever { str ->
            responsecode = 1;
            response = str.name
        }
        viewmodel.getStoreInfo()
        await().atMost(10, TimeUnit.SECONDS).until(getFakeCallable())
        assertEquals("My Fresh", viewmodel.getStoreInfoLD().value?.name)
    }

    @Test
    public fun testProductInfo() {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getProductInfoResponse())
        mockWebServer.enqueue(mockResponse)
        viewmodel.getProductListLD().observeForever { list ->
            responsecode = list.size
        }
        viewmodel.getProductList()
        await().atMost(10, TimeUnit.SECONDS).until(getFakeCallable())
        assertEquals(9, viewmodel.getProductListLD().value?.size)
    }


    private fun getFakeCallable(): Callable<Boolean> {
        return Callable<Boolean> { responsecode != -1; }
    }

    private fun getStoreInfoResponse(): String {
        val inputStream =
            InstrumentationRegistry.getInstrumentation().targetContext.assets.open("storeInfo.json")
        val bytes = inputStream.readBytes()
        val text = String(bytes, StandardCharsets.UTF_8)
        inputStream.close()
        return text
    }

    private fun getProductInfoResponse(): String {
        val inputStream =
            InstrumentationRegistry.getInstrumentation().targetContext.assets.open("productInfo.json")
        val bytes = inputStream.readBytes()
        val text = String(bytes, StandardCharsets.UTF_8)
        inputStream.close()
        return text
    }

}