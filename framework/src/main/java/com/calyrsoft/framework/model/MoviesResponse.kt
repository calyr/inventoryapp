package com.calyrsoft.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class MoviesResponse(@SerialName("page") val page: Int,@SerialName("results") val results: List<Movie>)