package com.hariharan.mycom.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hariharan.mycom.R
import com.hariharan.mycom.data.ProductInfo
import com.hariharan.mycom.databinding.RecyclerViewItemBinding

class ProductAdapter(private val productList: List<ProductInfo>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productInfo = productList[position]
        if (productInfo.photoUrl !== null) {
            Glide.with(context)
                .load(productInfo.photoUrl)
                .into(holder.productLogo)
        } else {
            Glide.with(context).clear(holder.productLogo);
            holder.productLogo.setImageDrawable(null);
        }
        holder.productName.text = productInfo.name
        holder.productQuantity.text = productInfo.quantity
        holder.productPrice.text = "Rs. " + productInfo.price
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productLogo: ImageView = itemView.findViewById(R.id.product_logo)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
    }
}
