package com.android.zmtestkotlinwithmvvm.mainActivity

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.zmtestkotlinwithmvvm.MyApplication
import com.android.zmtestkotlinwithmvvm.models.banner.Data
import com.android.zmtestkotlinwithmvvm.models.banner.HomeApiModel
import com.android.zmtestkotlinwithmvvm.models.productList.ProductListModel
import com.android.zmtestkotlinwithmvvm.network.ApiServices
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mApiService : ApiServices
    private var bannerList : MutableLiveData<Data> = MutableLiveData()
    private var productList : MutableLiveData<com.android.zmtestkotlinwithmvvm.models.productList.Data> = MutableLiveData()
    public var isProgressBarVisible : ObservableField<Boolean> = ObservableField(false)

    init {
        (application as MyApplication).getRetrofitComponent().inject(this)
    }

    fun getAllBannerApi(marketCode : String){
        val  call : Call<HomeApiModel> = mApiService.getAllBanners(marketCode)
        call.enqueue(object : Callback<HomeApiModel>{
            override fun onResponse(call: Call<HomeApiModel>, response: Response<HomeApiModel>) {
                Log.e("MainActivityViewModel","getAllBannerApi :- "+Gson().toJson(response.body()))
                bannerList.postValue(response.body()!!.Data)
            }

            override fun onFailure(call: Call<HomeApiModel>, t: Throwable) {
                Log.e("MainActivityViewModel","getAllBannerApi :- "+t.message)
            }

        })
    }

    fun getProductList(page:Int, productTagId :Int, marketCode: String){
        isProgressBarVisible.set(true)
        val  call : Call<ProductListModel> = mApiService.getProductList(page,productTagId,marketCode)
        call.enqueue(object : Callback<ProductListModel>{
            override fun onResponse(call: Call<ProductListModel>, response: Response<ProductListModel>) {
                Log.e("MainActivityViewModel","getProductList :- "+Gson().toJson(response.body()))
                productList.postValue(response.body()?.Data)
                isProgressBarVisible.set(false)
            }

            override fun onFailure(call: Call<ProductListModel>, t: Throwable) {
                Log.e("MainActivityViewModel","getProductList :- "+t.message)
                isProgressBarVisible.set(false)
            }

        })
    }

    fun observeBannerDataList():MutableLiveData<Data>{
        return bannerList
    }

    fun observeProductDataList():MutableLiveData<com.android.zmtestkotlinwithmvvm.models.productList.Data>{
        return productList
    }
}