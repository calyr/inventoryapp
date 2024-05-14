package com.calyrsoft.data

import com.calyrsoft.domain.Movie

class MovieRepository(val remotDataSource: IRemoteDataSource) {

    suspend fun getMovies(): NetworkResult<List<Movie>> {
        var list = remotDataSource.getMovies()//would be return any data source
        return list;
    }
}