package com.calyrsoft.budgetapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.data.MovieRepository
import com.calyrsoft.data.NetworkResult
import com.calyrsoft.domain.Movie
import com.calyrsoft.framework.RemoteDataSource
import com.calyrsoft.framework.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    val movie: Movie = Movie(title = "Titulo", posterPath = "")
    var dispatcher = Dispatchers.IO
    var apiSerice = RetrofitBuilder.apiService
    val remoteDataSource = RemoteDataSource(apiSerice, dispatcher)
    val repository = MovieRepository(remoteDataSource);

    private val _moviesUiState = MutableStateFlow(MovieUIState(isLoading = true))
    val movieUIState: StateFlow<MovieUIState> = _moviesUiState.asStateFlow()
    data class MovieUIState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val movies : List<Movie> = emptyList()
    )

    fun getMovies() {
        _moviesUiState.value = MovieUIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getMovies()

            if ( result is NetworkResult.Success) {

                _moviesUiState.update {
                    it.copy(
                    isLoading = false, movies = result.data
                )
                }
            } else if ( result is NetworkResult.Error) {
                _moviesUiState.update {
                    it.copy(
                        isLoading = false, error = "my error"
                    )
                }
            } else {
                _moviesUiState.update {
                    it.copy(
                        isLoading = false, error = " simulado3"
                    )
                }
                }
            }
        }
}