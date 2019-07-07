package com.dominikpiotrowski.springrest.demo.dao;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface IMovieCustomRepo extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.title =:title")
    Optional<Movie> findByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE m.productionYear =:productionYear")
    List<Movie> findByProductionYear(Year productionYear);

    @Query("SELECT m FROM Movie m WHERE m.maker =:maker")
    List<Movie> findByMaker(String maker);

}
