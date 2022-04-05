package com.android.zmtestkotlinwithmvvm.darrger

import com.android.zmtestkotlinwithmvvm.mainActivity.MainActivityViewModel
import com.android.zmtestkotlinwithmvvm.network.RetrofitHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitHelper::class])
interface RetrofitComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
}