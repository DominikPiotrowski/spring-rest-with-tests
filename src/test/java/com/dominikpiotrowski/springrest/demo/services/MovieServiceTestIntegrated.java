package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.bootstrap.MovieBootstrap;
import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@DataJpaTest
public class MovieServiceTestIntegrated {

    @Autowired
    IMovieRepo iMovieRepo;
    @Autowired
    IMovieCustomRepo iMovieCustomRepo;
    @Autowired
    MovieService movieService;

    @Before
    public void setUp() {
        MovieBootstrap bootstrap = new MovieBootstrap(iMovieRepo, iMovieCustomRepo);
        bootstrap.addMovies();
    }

    @Test //OK
    public void addMovie() {
        Movie movie = Movie.builder().title("Wrath of Khan").productionYear(Year.of(1982)).maker("Meyer").build();
        movieService.addMovie(movie);
        assertThat(movie.getMaker(), equalTo("Meyer"));
        assertSame(movie.getId(), 5L);
    }

    @Test //OK
    public void findAll() {
        List<Movie> movies = (List<Movie>) iMovieRepo.findAll();
        assertNotNull(movies);
        assertThat(movies.size(), equalTo(4));
    }

    @Test
    public void findById() {
        Movie movie = movieService.findById(1L);
        assertThat(movie.getProductionYear(), equalTo(Year.of(1984)));
        assertThat(movie.getMaker(), equalTo("Cameron"));
    }

    @Test
    public void findByTitle() {
        Movie movie = movieService.findByTitle("Terminator");
        assertThat(movie.getId(), equalTo(1L));
        assertThat(movie.getMaker(), equalTo("Cameron"));
    }

    @Test
    public void findByProductionYear() {
        List movies = iMovieCustomRepo.findByProductionYear(Year.of(1982));
        assertThat(movies.size(), equalTo(1));
    }

    @Test
    public void findByMaker() {
        List movies = iMovieCustomRepo.findByMaker("Cameron");
        assertThat(movies.size(), equalTo(1));
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        movieService.deleteById(id);
        verify(movieService, times(1)).deleteById(id);
    }

    @Test
    public void updateMovie() {
        Long id = 1L;
        Movie movie = movieService.findById(id);
        Movie movieUpdated = movieService.updateMovie(1L, "Hoho", Year.of(9999), "Santa");
        iMovieRepo.save(movieUpdated);
        assertThat(movie.getId(), equalTo(movieUpdated.getId()));
        assertNotEquals(movie.getTitle(), movieUpdated.getTitle());
    }

    @Test
    public void patchMovie() {
    }
}