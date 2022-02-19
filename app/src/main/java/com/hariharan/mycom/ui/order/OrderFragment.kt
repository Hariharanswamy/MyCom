package com.hariharan.mycom.ui.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.R
import com.hariharan.mycom.data.ProductInfo
import com.hariharan.mycom.databinding.OrderFragmentBinding
import com.hariharan.mycom.ui.ProductAdapter
import java.lang.reflect.Type

class OrderFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFragment()
    }

    private lateinit var viewModel: OrderViewModel

    private lateinit var binding: OrderFragmentBinding

    private val args: OrderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()

        val selectedList : List<ProductInfo> = Gson().fromJson(args.productList, listType)

        binding.backButton.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
        binding.proceedButton.setOnClickListener(View.OnClickListener {
            if(binding.addressText.text.isNotEmpty()) {
                findNavController().navigate(R.id.order_success)
            } else {
                Toast.makeText(activity, "Enter address to proceed.", Toast.LENGTH_SHORT).show()
            }
        })
        binding.productsList.layoutManager = LinearLayoutManager(activity)
        val adapter: ProductAdapter =
            ProductAdapter(selectedList, object : ProductAdapter.ItemClickListener {
                override fun onItemAdd(position: Int) {
                }

            }, true)
        binding.productsList.adapter = adapter
    }

}