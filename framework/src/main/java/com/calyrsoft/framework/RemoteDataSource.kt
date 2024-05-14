package com.calyrsoft.framework

import com.calyrsoft.data.IRemoteDataSource
import com.calyrsoft.data.NetworkResult
import com.calyrsoft.domain.Movie
import com.calyrsoft.framework.model.MoviesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(val apiService: ApiService, val dispatcher: CoroutineDispatcher): IRemoteDataSource {

    override suspend fun getMovies(): NetworkResult<List<Movie>> {
        return withContext(dispatcher) {
            val response = apiService.listPopularMovies("fa3e844ce31744388e07fa47c7c5d8c3")

            if( response.isSuccessful ) {
                val list = response.body()!!.results.map{
                    it.toMovieDomain()
                }
                NetworkResult.Success(data = list)
            } else {
                NetworkResult.Error(error = response.errorBody().toString())
            }
        }
    }
}