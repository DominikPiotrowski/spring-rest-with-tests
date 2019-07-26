package com.dominikpiotrowski.springrest.demo.dao;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;

import java.util.List;

@Repository
public interface IMovieCustomRepo extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.title =:title")
    Movie findByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE m.productionYear =:productionYear")
    List findByProductionYear(Year productionYear);

    @Query("SELECT m FROM Movie m WHERE m.maker =:maker")
    List findByMaker(String maker);
}