package com.example.dynamicviewpagertestapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class PageResponse(
    val type: String,
    val payload: @RawValue Payload
)

data class Payload(
    val text: String,
    val url: String
)
