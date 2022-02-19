package com.hariharan.mycom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hariharan.mycom.ui.NavHostFragment
import com.hariharan.mycom.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NavHostFragment.newInstance())
                    .commitNow()
        }
    }
}