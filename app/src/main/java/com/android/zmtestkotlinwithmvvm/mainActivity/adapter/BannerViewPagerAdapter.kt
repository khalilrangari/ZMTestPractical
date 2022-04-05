package com.android.zmtestkotlinwithmvvm.mainActivity.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.android.zmtestkotlinwithmvvm.R
import com.android.zmtestkotlinwithmvvm.mainActivity.ViewPagerFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.*
import javax.xml.validation.SchemaFactory.newInstance
import kotlin.collections.ArrayList


class BannerViewPagerAdapter(
    private var context: Context,
    private var bannerList: MutableList<String>
) : PagerAdapter() {

    override fun getCount(): Int {
        return bannerList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        this.context = container.context
        val mLayoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.banner_item, container, false)
        val imageView = itemView.findViewById<View>(R.id.ivBanner) as ImageView
        Glide.with(context)
            .load(this.bannerList[position])
            .into(imageView)
        Objects.requireNonNull(container).addView(itemView)
        return itemView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun setData(data: MutableList<String>){
        this.bannerList.addAll(data)
        notifyDataSetChanged()
    }
}
