package com.hariharan.mycom.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hariharan.mycom.R
import com.hariharan.mycom.data.model.ProductInfo

/**
 * Adapter to show product list.
 */
class ProductAdapter(
    private val productList: List<ProductInfo>,
    private val clickListener: ItemClickListener,
    private val orderSummary: Boolean
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productInfo = productList[position]
        if (productInfo.photoUrl !== null) {
            Glide.with(context)
                .load(productInfo.photoUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.productLogo)
        } else {
            Glide.with(context).clear(holder.productLogo)
            holder.productLogo.setImageDrawable(null)
        }
        holder.productName.text = productInfo.name
        holder.productQuantity.text = productInfo.quantity
        holder.productPrice.text = "Rs. " + productInfo.price
        if (orderSummary) {
            holder.addButton.visibility = View.INVISIBLE
        } else {
            holder.addButton.visibility = View.VISIBLE
            holder.addButton.setOnClickListener(View.OnClickListener {
                clickListener.onItemAdd(position)
                holder.addButton.isEnabled = false
            })
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productLogo: ImageView = itemView.findViewById(R.id.product_logo)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val addButton: Button = itemView.findViewById(R.id.add_button)
    }

    interface ItemClickListener {
        fun onItemAdd(position: Int)
    }
}
