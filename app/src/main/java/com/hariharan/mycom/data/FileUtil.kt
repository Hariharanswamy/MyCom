package com.hariharan.mycom.data

import android.app.Application

class FileUtil(private val application: Application) {

    fun readAssetFile(fileName: String): String {
        val jsonString = application.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        return jsonString;
    }
}