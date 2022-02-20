package com.hariharan.mycom.ui.order

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hariharan.mycom.R
import com.hariharan.mycom.data.model.ProductInfo
import com.hariharan.mycom.databinding.OrderFragmentBinding
import com.hariharan.mycom.ui.ProductAdapter
import com.hariharan.mycom.ui.progress.ProgressDialogFragment
import java.lang.reflect.Type

/**
 * Screen to display order summary
 */
class OrderFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFragment()
    }

    private lateinit var viewModel: OrderViewModel

    private lateinit var binding: OrderFragmentBinding

    private var progressDialogFragment = ProgressDialogFragment()

    private val args: OrderFragmentArgs by navArgs()

    val handler = Handler()

    private val orderSummaryObserver = Observer<String> {
        handler.postDelayed({
            progressDialogFragment.dismiss()
            findNavController().navigate(R.id.order_success)
        }, 5000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        viewModel.getOrderSummaryLD().observe(viewLifecycleOwner, orderSummaryObserver)

        val listType: Type = object : TypeToken<List<ProductInfo?>?>() {}.getType()
        val selectedList: List<ProductInfo> = Gson().fromJson(args.productList, listType)

        var orderAmount = 0
        selectedList.forEach { productInfo ->
            orderAmount += productInfo.price!!
        }
        binding.totalBill.text = "Rs. " + orderAmount

        binding.proceedButton.setOnClickListener {
            if (binding.addressText.text.isNotEmpty()) {
                progressDialogFragment.show(parentFragmentManager, "ProgressDialog")
                viewModel.sendProductList(selectedList)
            } else {
                Toast.makeText(activity, R.string.enter_address, Toast.LENGTH_SHORT).show()
            }
        }
        binding.productsList.layoutManager = LinearLayoutManager(activity)
        val adapter =
            ProductAdapter(selectedList, object : ProductAdapter.ItemClickListener {
                override fun onItemAdd(position: Int) {
                }
            }, true)
        binding.productsList.adapter = adapter
    }

}