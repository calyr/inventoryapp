package com.calyrsoft.framework

import com.calyrsoft.domain.Movie as MovieDomain
import com.calyrsoft.framework.model.Movie as MovieRemote
fun MovieRemote.toMovieDomain() : MovieDomain {
    return MovieDomain(
        title = title,
        posterPath = posterPath
    )
}