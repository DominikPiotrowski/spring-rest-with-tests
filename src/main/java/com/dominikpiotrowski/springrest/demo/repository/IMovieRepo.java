package com.dominikpiotrowski.springrest.demo.repository;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface IMovieRepo extends CrudRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);
    List<Movie> findByProductionYear(Year year);
    List<Movie> findByMaker(String maker);
}