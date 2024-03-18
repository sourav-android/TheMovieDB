package com.android.themoviedb.pagingonly

import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, MovieModel> {

    override fun mapToDomainModel(model: MovieDto): MovieModel {
        return MovieModel(
            id = model.id,
            overview = model.overview.orEmpty(),
            posterPath = model.posterPath.orEmpty(),
            releaseDate = model.releaseDate.orEmpty(),
            title = model.title.orEmpty(),
            voteAverage = model.voteAverage
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<MovieModel> {
        return initial.map {
            mapToDomainModel(it)
        }
    }



    override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
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

        fun fromDomainList(initial: List<MovieModel>): List<MovieDto> {
            return initial.map {
                mapFromDomainModel(it)
            }
        }


}


