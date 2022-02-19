package com.hariharan.mycom.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.hariharan.mycom.R
import com.hariharan.mycom.databinding.FragmentSplashScreenBinding
import com.hariharan.mycom.databinding.MainFragmentBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding:FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.getStarted.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.splash_home)
        })
    }
}