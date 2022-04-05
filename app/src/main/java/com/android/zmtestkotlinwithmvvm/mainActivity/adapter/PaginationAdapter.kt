package com.android.zmtestkotlinwithmvvm.mainActivity.adapter

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.zmtestkotlinwithmvvm.R
import com.android.zmtestkotlinwithmvvm.models.productList.Market
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_product_view.view.*


class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var productList: List<Market> = ArrayList()
    private val isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when(viewType){
            VIEW_TYPE_NORMAL->{
                val viewItem: View = inflater.inflate(
                    R.layout.item_product_view,
                    parent,
                    false
                )
                val height: Int = parent.measuredHeight / 4
                viewItem.minimumHeight = height
                viewHolder = ProductItemViewHolder(viewItem)
            }
            VIEW_TYPE_LOADING->{
                val viewItem: View = inflater.inflate(
                    R.layout.item_product_view,
                    parent,
                    false
                )
                viewHolder = LoadingItemViewHolder(viewItem)
            }
        }


        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
//        val devicewidth = displaymetrics.widthPixels / 3
        val deviceheight = displaymetrics.heightPixels / 4
//        holder.itemView.ivProduct.getLayoutParams().width = devicewidth
        holder.itemView.ivProduct.getLayoutParams().height = deviceheight

        val product = productList[position]
        val productViewHolder: ProductItemViewHolder = holder as ProductItemViewHolder
//                productViewHolder.movieTitle.setText(product.name)
        Glide.with(context).load(product.imgUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(productViewHolder.itemView.ivProduct)
    }

    class ProductItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)
    class LoadingItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)

    override fun getItemCount(): Int {
        return if (productList.isNotEmpty()) productList.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == productList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }
    fun setUpProductData(productList: List<Market>){
        this.productList = productList
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

}