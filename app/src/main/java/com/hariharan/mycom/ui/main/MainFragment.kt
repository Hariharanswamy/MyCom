package com.hariharan.mycom.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.R
import com.hariharan.mycom.data.model.ProductInfo
import com.hariharan.mycom.data.model.StoreInfo
import com.hariharan.mycom.databinding.MainFragmentBinding
import com.hariharan.mycom.ui.ProductAdapter
import com.hariharan.mycom.ui.progress.ProgressDialogFragment
import java.lang.reflect.Type

/**
 * Screen which displays the store and product information.
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    private var selectedProducts: List<ProductInfo> = emptyList()

    private var progressDialogFragment = ProgressDialogFragment()

    private val storeInfoObserver = Observer<StoreInfo> { storeInfo ->
        Log.i("Store info", storeInfo.toString())
        binding.storeName.text = storeInfo.name
        binding.storeAddress.text = storeInfo.address1 + "\n" + storeInfo.address2 + storeInfo.city
        if (storeInfo.logo !== null) {
            Glide.with(this)
                .load(storeInfo.logo)
                .placeholder(R.drawable.placeholder)
                .into(binding.storeLogo)
        }
    }

    private val productListObserver = Observer<List<ProductInfo>> { productList ->
        progressDialogFragment.dismiss()
        val adapter: ProductAdapter =
            ProductAdapter(productList, object : ProductAdapter.ItemClickListener {
                override fun onItemAdd(position: Int) {
                    selectedProducts = selectedProducts + productList[position]
                }

            }, false)
        binding.productsList.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getStoreInfoLD().observe(viewLifecycleOwner, storeInfoObserver)
        viewModel.getProductListLD().observe(viewLifecycleOwner, productListObserver)
        progressDialogFragment.show(parentFragmentManager, "ProgressDialog")

        viewModel.getStoreInfo()
        viewModel.getProductList()

        binding.productsList.layoutManager = LinearLayoutManager(activity)
        binding.proceedButton.setOnClickListener(View.OnClickListener {
            if (selectedProducts.isNotEmpty()) {
                val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()
                val gson = Gson().toJson(selectedProducts, listType )
                val nav = MainFragmentDirections.mainOrder(gson)
                findNavController().navigate(nav)
            } else {
                Toast.makeText(activity, R.string.product_screen_toast, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}