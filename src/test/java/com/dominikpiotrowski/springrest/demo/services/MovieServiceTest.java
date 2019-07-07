package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieServiceTest {

    @Autowired
    IMovieRepo iMovieRepo;
    IMovieCustomRepo iMovieCustomRepo;
    MovieService movieService;

    @Before
    public void setUp() throws Exception {
        MovieService movieService = new MovieService(iMovieRepo, iMovieCustomRepo);
        movieService.addMovies();
    }

    @Test //OK
    public void addMovie() {
        Movie movie = Movie.builder().title("Wrath of Khan").productionYear(Year.of(1982)).maker("Meyer").build();
        iMovieRepo.save(movie); //to działa?
        // movieService.addMovie(movie); //dlaczego tu null pointer?
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
        Optional<Movie> movie = iMovieRepo.findById(5L);
        assertThat(movie.getMaker(), equalTo("Meyer")); //dlaczego nie mam dostępu do metod Movie?
    }

    @Test
    public void findByTitle() {
        Optional<Movie> movie = iMovieCustomRepo.findByTitle("Wrath of Khan");
        assertThat(movie.getMaker(), equalTo("Meyer"));
    }

    @Test
    public void findByProductionYear() {
        List<Movie> movies = iMovieCustomRepo.findByProductionYear(Year.of(1982));
        assertThat(movies.getMaker(), equalTo("Meyer"));
    }

    @Test
    public void findByMaker() {
        List<Movie> movies = iMovieCustomRepo.findByMaker("Meyer"); // tu leci nullPointer...?
        assertThat(movies.size(), equalTo(1));
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        movieService.deleteById(id); // tu leci nullPointer...?
        verify(movieService, times(1)).deleteById(id);
    }

    @Test
    public void updateMovie() {
    }

    @Test
    public void patchMovie() {
    }
}