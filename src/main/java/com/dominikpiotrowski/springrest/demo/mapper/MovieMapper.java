package com.dominikpiotrowski.springrest.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;


@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    MovieDataTransfer movieToMovieDto(Movie movie);
    Movie movieDtoToMovie(MovieDataTransfer movieDataTransfer);
}