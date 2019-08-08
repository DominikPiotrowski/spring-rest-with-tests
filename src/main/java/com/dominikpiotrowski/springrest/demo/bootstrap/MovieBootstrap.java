package com.dominikpiotrowski.springrest.demo.bootstrap;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.repository.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.stream.Stream;

import java.time.Year;

@Component
public class MovieBootstrap implements CommandLineRunner {

    private IMovieRepo iMovieRepo;

    @Autowired
    public MovieBootstrap(IMovieRepo iMovieRepo){
        this.iMovieRepo = iMovieRepo;
    }

    @Override
    public void run(String... args) {
        addMovies();
    }

    public void addMovies() {

        Movie movie1 = new Movie();
        movie1.setMaker("Cameron");
        movie1.setTitle("Terminator");
        movie1.setProductionYear(Year.of(1984));

        Movie movie2 = new Movie();
        movie1.setMaker("Cubrick");
        movie1.setTitle("Space Oddysey");
        movie1.setProductionYear(Year.of(1968));

        Movie movie3 = new Movie();
        movie1.setMaker("Snyder");
        movie1.setTitle("Watchmen");
        movie1.setProductionYear(Year.of(2009));

        Movie movie4 = new Movie();
        movie1.setMaker("Wise");
        movie1.setTitle("Star Trek");
        movie1.setProductionYear(Year.of(1979));

        Stream.of(movie1,movie2,movie3,movie4).forEach(iMovieRepo::save);


    }
}