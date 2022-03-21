package com.example.dynamicviewpagertestapp.di

import com.example.dynamicviewpagertestapp.MainViewModel
import com.example.dynamicviewpagertestapp.dataSource.ApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://demo7877231.mockable.io/"

val mainModule = module {
    factory <ApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    viewModel { MainViewModel(get()) }
}
