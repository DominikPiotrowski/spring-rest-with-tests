package com.dominikpiotrowski.springrest.demo.bootstrap;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class MovieBootstrap implements CommandLineRunner {

    private IMovieRepo iMovieRepo;
    private IMovieCustomRepo iMovieCustomRepo;

    @Autowired
    public MovieBootstrap(IMovieRepo iMovieRepo, IMovieCustomRepo iMovieCustomRepo){
        this.iMovieRepo = iMovieRepo;
        this.iMovieCustomRepo = iMovieCustomRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        addMovies();
    }

  //  @EventListener(ApplicationReadyEvent.class)
    public void addMovies() {
        addMovie(new Movie(1L, "Terminator", Year.of(1984), "Cameron"));
        addMovie(new Movie(2L, "Space Oddysey", Year.of(1968), "Cubrick"));
        addMovie(new Movie(3L, "Watchmen", Year.of(2009), "Snyder"));
        addMovie(new Movie(4L, "Star Trek", Year.of(1979), "Wise"));
    }

    public void addMovie(Movie movie) {
        iMovieRepo.save(movie);
    }
}