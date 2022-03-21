package com.example.dynamicviewpagertestapp.dataSource

import com.example.dynamicviewpagertestapp.domain.PageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/v1/hot")
    suspend fun getNotes(): Response<List<Int>?>

    @GET("api/v1/post/{id}")
    suspend fun getNoteById(@Path("id") id: Int): Response<PageResponse?>
}