package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
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
//        MovieService movieService = new MovieService(iMovieRepo, iMovieCustomRepo);
//        Movie m1 = new Movie(1L, "Terminator", Year.of(1984), "Cameron");
//        Movie m2 = new Movie(2L, "Space Oddysey", Year.of(1968), "Cubrick");
//        Movie m3 = new Movie(3L, "Watchmen", Year.of(2009), "Snyder");
//        Movie m4 = new Movie(4L, "Star Trek", Year.of(1979), "Wise");
//        movieService.addMovie(m1);
//        movieService.addMovie(m2);
//        movieService.addMovie(m3);
//        movieService.addMovie(m4);

        movieService.addMovies(); //TODO  powinno dodać filmy
    }

    @Test //OK
    public void addMovie() {
        Movie movie = Movie.builder().title("Wrath of Khan").productionYear(Year.of(1982)).maker("Meyer").build();
        movieService.addMovie(movie); //TODO null pointer...?
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
        Movie movie = movieService.findById(1L); //TODO null pointer...?
        assertThat(movie.getProductionYear(), equalTo(Year.of(1984)));
        assertThat(movie.getMaker(), equalTo("Cameron"));
    }

    @Test
    public void findByTitle() {
        Movie movie = movieService.findByTitle("Terminator"); //TODO null pointer...?
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
        List movies = iMovieCustomRepo.findByMaker("Cameron"); //TODO null pointer...?
        assertThat(movies.size(), equalTo(1));
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        movieService.deleteById(id); //TODO null pointer...?
        verify(movieService, times(1)).deleteById(id);
    }

    @Test
    public void updateMovie() {
        Long id = 1L;
        Movie movie = movieService.findById(id); //TODO null pointer...?
        Movie movieUpdated = movieService.updateMovie(1L, "Hoho", Year.of(9999), "Santa");
        iMovieRepo.save(movieUpdated);
        assertThat(movie.getId(), equalTo(movieUpdated.getId()));
        assertNotEquals(movie.getTitle(), movieUpdated.getTitle());
    }

    @Test
    public void patchMovie() {
    }
}