package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.bootstrap.MovieBootstrap;
import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.mapper.MovieMapper;
import com.dominikpiotrowski.springrest.demo.repository.IMovieRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase
public class MovieServiceTestIntegrated {

    @Autowired
    IMovieRepo iMovieRepo;
    MovieService movieService;

    @Before
    public void setUp() {
        MovieBootstrap bootstrap = new MovieBootstrap(iMovieRepo);
        bootstrap.addMovies();
        movieService = new MovieService(iMovieRepo, MovieMapper.INSTANCE);
    }

    @Test
    public void addMovie() {
        MovieDataTransfer movieDataTransfer = MovieDataTransfer.builder().title("Wrath of Khan").production(1982).maker("Meyer").build();
        movieService.addMovie(movieDataTransfer);

        List<MovieDataTransfer> movies =  movieService.findAll();
        assertNotNull(movies);
        assertThat(movies.size(), equalTo(5));
    }

    @Test
    public void findAll() {
        List<MovieDataTransfer> movies =  movieService.findAll();
        assertNotNull(movies);
        assertThat(movies.size(), equalTo(4));
    }

    @Test
    public void getMovieById() {
        MovieDataTransfer movieDataTransfer = movieService.getMovieById(1L);
        assertThat(movieDataTransfer.getProduction(), equalTo(1979));
        assertThat(movieDataTransfer.getMaker(), equalTo("Wise"));
    }

    @Test
    public void findByTitle() {
        MovieDataTransfer movieDataTransfer = movieService.getMovieByTitle("Star Trek");
        assertThat(movieDataTransfer.getMaker(), equalTo("Wise"));
    }

    @Test
    public void findByProductionYear() {
        List movies = movieService.getMovieByProduction(1979);
        assertThat(movies.size(), equalTo(1));
    }

    @Test
    public void findByMaker() {
        List movies = movieService.getMovieByMaker("Wise");
        assertThat(movies.size(), equalTo(1));
    }

    @Test(expected = NoSuchMovieException.class)
    public void deleteById() {

        Long id = 2L;
        movieService.deleteById(id);
        movieService.getMovieById(2L);
    }

    @Test
    public void updateMovie() {
        Long id = 2L;
        MovieDataTransfer movieDataTransfer = movieService.getMovieById(id);
        MovieDataTransfer updater = MovieDataTransfer.builder().id(2L).title("Hoho").production(9999).maker("Santa").build();
        MovieDataTransfer movieUpdated = movieService.updateMovie(id, updater);

        assertThat(movieDataTransfer.getId(), equalTo(movieUpdated.getId()));
        assertThat(movieUpdated.getTitle(), equalTo("Hoho"));
        assertNotEquals("Terminator", movieUpdated.getTitle());
    }

    @Test
    public void patchMovie() {
        Long id = 1L;
        MovieDataTransfer movieDataTransfer = movieService.getMovieById(id);
        MovieDataTransfer updater = MovieDataTransfer.builder().title("Holy Hand Grenade of Antioch").build();
        MovieDataTransfer moviePatched = movieService.patchMovie(id, updater);

        assertNotEquals(movieDataTransfer, moviePatched);
        assertNotEquals(movieDataTransfer.getTitle(), moviePatched.getTitle());
        assertThat(moviePatched.getTitle(), equalTo("Holy Hand Grenade of Antioch"));
        assertThat(moviePatched.getProduction(), equalTo(1979));
    }
}