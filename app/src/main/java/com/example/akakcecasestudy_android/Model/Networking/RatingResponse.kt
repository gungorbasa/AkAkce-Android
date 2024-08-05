package com.example.akakcecasestudy_android.Model.Networking

import kotlinx.serialization.Serializable

@Serializable
data class RatingResponse(
    val rate: Double,
    val count: Long
)