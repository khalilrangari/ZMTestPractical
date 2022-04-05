package com.android.zmtestkotlinwithmvvm.network

import com.android.zmtestkotlinwithmvvm.models.banner.HomeApiModel
import com.android.zmtestkotlinwithmvvm.models.productList.ProductListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/home")
    fun getAllBanners(@Query("marketCode") marketCode : String) : Call<HomeApiModel>

    @GET("/productlist")
    fun getProductList(@Query("page") page : Int,
                       @Query("productTagId") productTagId : Int,
                       @Query("marketCode") marketCode : String) : Call<ProductListModel>
}