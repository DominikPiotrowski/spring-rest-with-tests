package com.dominikpiotrowski.springrest.demo.dao;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;

@Repository
public interface IMovieRepo extends CrudRepository<Movie, Long> {
}