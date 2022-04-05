package com.android.zmtestkotlinwithmvvm

import android.app.Application
import com.android.zmtestkotlinwithmvvm.darrger.DaggerRetrofitComponent
import com.android.zmtestkotlinwithmvvm.darrger.RetrofitComponent
import com.android.zmtestkotlinwithmvvm.network.ApiServices
import com.android.zmtestkotlinwithmvvm.network.RetrofitHelper
import com.android.zmtestkotlinwithmvvm.repository.AllBannersRepository

class MyApplication : Application() {

    private lateinit var retrofitComponent: RetrofitComponent

    override fun onCreate() {
        super.onCreate()
        retrofitComponent = DaggerRetrofitComponent.builder()
            .retrofitHelper(RetrofitHelper())
            .build()
    }

    fun getRetrofitComponent(): RetrofitComponent {
        return retrofitComponent
    }
}