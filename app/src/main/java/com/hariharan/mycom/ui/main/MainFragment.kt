package com.hariharan.mycom.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hariharan.mycom.data.StoreInfo
import com.hariharan.mycom.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    private val storeInfoObserver = Observer<StoreInfo> { storeInfo ->
        Log.i("Store info", storeInfo.toString())
        binding.storeName.text = storeInfo.name
        binding.storeAddress.text = storeInfo.address1 + "\n" +storeInfo.address2 + storeInfo.city
        if (storeInfo.logo !== null) {
            Glide.with(this)
                .load(storeInfo.logo)
                .into(binding.storeLogo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getStoreInfo()
        viewModel.getStoreInfoLD().observe(viewLifecycleOwner, storeInfoObserver)
    }

}