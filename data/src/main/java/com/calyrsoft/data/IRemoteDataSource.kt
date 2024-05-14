package com.calyrsoft.data

import com.calyrsoft.domain.Movie

interface IRemoteDataSource {

    suspend fun getMovies(): NetworkResult<List<Movie>>
}