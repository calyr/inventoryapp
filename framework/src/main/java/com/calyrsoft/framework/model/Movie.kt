package com.calyrsoft.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("title") val title: String,
    @SerialName("poster_path") val posterPath: String
)