package com.hariharan.mycom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hariharan.mycom.R
import com.hariharan.mycom.ui.main.MainFragment

class NavHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_host, container, false)
    }

    companion object {
        fun newInstance() = NavHostFragment()
    }
}