package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ComponentScan("com.dominikpiotrowski.springrest.demo")
@DataJpaTest

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @DirtiesContext - odświeżanie kontekstu springa po każdym teście - sprawia to, że baza danych jest budowana na nowo
// wydłuża to wykonanie testów, także nie mozna tego nadużywać
// przy dużej liczbie testów zamiast używania tej adnotacji każdy test, który modyfikuje dane powinien sprzątać bazę po sobie (przywrócić usunięte obiekty, usunąć dodane, itp)
public class MovieServiceTestIntegrated {

    @Autowired
    MovieService movieService;

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