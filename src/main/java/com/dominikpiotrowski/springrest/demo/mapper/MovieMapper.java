package com.dominikpiotrowski.springrest.demo.mapper;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    MovieDataTransfer movieToMovieDto(Movie movie);
    Movie movieDtoToMovie(MovieDataTransfer movieDataTransfer);
}
