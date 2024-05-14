package com.calyrsoft.framework

import com.calyrsoft.framework.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?short_by=popularity.desc")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String) : Response<MoviesResponse>
}
