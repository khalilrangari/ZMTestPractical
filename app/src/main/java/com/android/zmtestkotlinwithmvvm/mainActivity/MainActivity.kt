package com.android.zmtestkotlinwithmvvm.mainActivity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.android.zmtestkotlinwithmvvm.databinding.ActivityMainBinding
import com.android.zmtestkotlinwithmvvm.mainActivity.adapter.BannerViewPagerAdapter
import com.android.zmtestkotlinwithmvvm.mainActivity.adapter.PaginationAdapter
import com.android.zmtestkotlinwithmvvm.models.banner.BrandZoneBanner
import com.android.zmtestkotlinwithmvvm.models.banner.MainBanner
import com.android.zmtestkotlinwithmvvm.models.banner.PromotionalBanner
import com.android.zmtestkotlinwithmvvm.models.banner.PromotionalBanner2
import com.android.zmtestkotlinwithmvvm.models.productList.Market
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val MARKET_CODE: String = "UZ"
        const val PRODUCT_TARGET_ID: Int = 13
    }

    private lateinit var viewModel: MainActivityViewModel
    private var bannerImageUrlList: MutableList<String> = mutableListOf()
    private lateinit var mBinding: ActivityMainBinding
    private var mProductAdapter = PaginationAdapter()
    private lateinit var layoutManager: GridLayoutManager
    private var pageNo = 1
    private var isLoading = false
    private var isLastPage = false
    private var productList: MutableList<Market> = mutableListOf()
    private lateinit var adapter : BannerViewPagerAdapter
    var timerTask: TimerTask? = null
    var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(mBinding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBinding.etSearch.isFocusable = false
            mBinding.etSearch.isSelected = false
            mBinding.viewPager.requestFocus()
        }
        adapter = BannerViewPagerAdapter(this,bannerImageUrlList)
        initProductView()
        initViewModel(MARKET_CODE)
        subscribBannerData()
        subscribProductData()
    }

    private fun initProductView() {
        layoutManager = GridLayoutManager(this, 2)
        mBinding.rvProductList.layoutManager = layoutManager
        mBinding.rvProductList.adapter = mProductAdapter
    }

    private fun subscribProductData() {
        viewModel.observeProductDataList().observe(this, Observer { it ->
            productList.addAll(it.marketList)
            mProductAdapter.setUpProductData(productList)
            mProductAdapter.notifyDataSetChanged()
            isLoading = false
            isLastPage = false
        })

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mBinding.rvProductList.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                this@MainActivity.isLoading = true
                pageNo++
                callProductApi(pageNo, PRODUCT_TARGET_ID, MARKET_CODE)
            }

            override fun isLastPage(): Boolean {
                return this@MainActivity.isLastPage
            }

            override fun isLoading(): Boolean {
                return this@MainActivity.isLoading
            }
        })
    }

    private fun subscribBannerData() {
        viewModel.observeBannerDataList().observe(this, Observer { it ->
            for (mainBanner: MainBanner in it.mainBanner) {
                bannerImageUrlList.add(mainBanner.imageUrl)
            }
            for (brandZoneBanner: BrandZoneBanner in it.brandZoneBanner) {
                bannerImageUrlList.add(brandZoneBanner.imageUrl)
            }
            for (promotionalBanner: PromotionalBanner in it.promotionalBanner) {
                bannerImageUrlList.add(promotionalBanner.imageUrl)
            }
            for (promotionalBanner2: PromotionalBanner2 in it.promotionalBanner2) {
                bannerImageUrlList.add(promotionalBanner2.imageUrl)
            }

            adapter.setData(bannerImageUrlList)
        })
        initBannerView()
    }

    private fun initBannerView() {
        Log.e("MainActivity", "sizeeee-- " + bannerImageUrlList.size)
        mBinding.viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        setAutoScrollListener(mBinding.viewPager,adapter.count)
    }

    fun initViewModel(marketCode: String) {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mBinding.viewModel = viewModel
        viewModel.getAllBannerApi(marketCode)
        callProductApi(pageNo, PRODUCT_TARGET_ID, MARKET_CODE)
    }

    fun callProductApi(pageNo: Int, productTargetId: Int, marketCode: String) {
        Toast.makeText(this@MainActivity, "page No :- " + pageNo, Toast.LENGTH_SHORT).show()
        viewModel.getProductList(pageNo, productTargetId, marketCode)
    }

    /*set page auto scroll listener*/
    private fun setAutoScrollListener(viewPager: ViewPager, count: Int) {
        timerTask = object : TimerTask() {
            override fun run() {
                viewPager.post { viewPager.setCurrentItem(if (count - 1 === viewPager.getCurrentItem()) 0 else viewPager.currentItem + 1) }
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 3000, 3000)
    }
}