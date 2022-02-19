package com.hariharan.mycom.ui.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hariharan.mycom.R
import com.hariharan.mycom.databinding.FragmentSuccessBinding

/**
 * Screen to display order success message
 */
class SuccessFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gotIt.setOnClickListener {
            findNavController().navigate(R.id.action_successFragment_to_mainFragment)
        }
    }
}