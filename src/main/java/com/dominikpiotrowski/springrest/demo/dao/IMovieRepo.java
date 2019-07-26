package com.dominikpiotrowski.springrest.demo.dao;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IMovieRepo extends CrudRepository<Movie, Long> {
}