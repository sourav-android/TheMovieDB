package com.android.themoviedb.data.remote.mapper

import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.domain.util.DomainMapper

class MovieEntityMapper : DomainMapper<MovieDto, MovieEntity> {

    override fun mapToDomainModel(model: MovieDto): MovieEntity {
        return MovieEntity(
            id = model.id,
            overview = model.overview.orEmpty(),
            posterPath = model.posterPath.orEmpty(),
            releaseDate = model.releaseDate.orEmpty(),
            title = model.title.orEmpty(),
            voteAverage = model.voteAverage
        )
    }

    fun fromEntityList(initial: List<MovieDto>): List<MovieEntity> {
        return initial.map {
            mapToDomainModel(it)
        }
    }

    override fun mapFromDomainModel(domainModel: MovieEntity): MovieDto {
        return MovieDto(
            id = domainModel.id,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            title = domainModel.title,
            voteAverage = domainModel.voteAverage,
            adult = false,
            backdropPath = "",
            genreIds = listOf(),
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            video = false,
            voteCount = 0

            )
    }

    fun toEntityList(initial : List<MovieEntity>) : List<MovieDto> {
        return initial.map {
            mapFromDomainModel(it)
        }
    }



}