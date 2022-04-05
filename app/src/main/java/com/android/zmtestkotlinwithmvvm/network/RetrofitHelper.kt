package com.android.zmtestkotlinwithmvvm.network

import com.android.zmtestkotlinwithmvvm.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitHelper {

    @Singleton
    @Provides
    fun getRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterface(retrofit: Retrofit) : ApiServices{
        return  retrofit.create(ApiServices::class.java)
    }
}