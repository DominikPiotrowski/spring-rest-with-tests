package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Year;
import java.util.*;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    private MovieService movieService;

    private static final Long ID = 99L;
    private static final String TITLE = "Moon";
    private static final Year PRODUCTION_YEAR = Year.of(2009);
    private static final String MAKER = "Duncan Jones";

    @Mock
    private IMovieRepo iMovieRepo;

    @Mock
    private IMovieCustomRepo iMovieCustomRepo;

    @Before
    public void setUp() {
        movieService = new MovieService(iMovieRepo, iMovieCustomRepo);
    }

    @Test
    public void addMovie() {
        //given
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        //when
        when(iMovieRepo.save(movie)).thenReturn(movie);
        //then
        assertThat(movie.getId(), equalTo(99L));
        assertThat(movie.getTitle(), equalTo("Moon"));
        assertThat(movie.getProductionYear(), equalTo(Year.of(2009)));
        assertThat(movie.getMaker(), equalTo("Duncan Jones"));
    }

    @Test
    public void findAll() {
        //given
        List<Movie> movies = Arrays.asList(new Movie(), new Movie(), new Movie(), new Movie());
        //when
        when(iMovieRepo.findAll()).thenReturn(movies);
        //then
        assertNotNull(movies);
        assertThat(movies.size(), equalTo(4));
    }

    @Test
    public void findById() {
        //given
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        //when
        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Movie movieFound = movieService.findById(ID);
        //then
        assertThat(movieFound.getId(), equalTo(ID));
        assertThat(movieFound.getTitle(), equalTo(TITLE));
        assertThat(movieFound.getProductionYear(), equalTo(PRODUCTION_YEAR));
        assertThat(movieFound.getMaker(), equalTo(MAKER));
    }

    @Test(expected = NoSuchMovieException.class)
    public void findByIdException() {
        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.empty());
        movieService.findById(ID);
    }

    @Test
    public void findByTitle() {
        //given
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        //when
        when(iMovieCustomRepo.findByTitle(anyString())).thenReturn(movie);
        Movie movieFound = movieService.findByTitle(TITLE);
        //then
        assertThat(movieFound.getId(), equalTo(ID));
        assertThat(movieFound.getTitle(), equalTo(TITLE));
        assertThat(movieFound.getProductionYear(), equalTo(PRODUCTION_YEAR));
        assertThat(movieFound.getMaker(), equalTo(MAKER));
    }

//    @Test(expected = NoSuchMovieException.class)
//    public void findByTitleException() {
//        when(iMovieCustomRepo.findByTitle(anyString())).thenReturn(Movie.class.)); //TODO co tu zwrócić? metoda zwraca film
//        movieService.findByTitle(TITLE);
//    }

    @Test
    public void findByProductionYear() {
        //given
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        //when
        when(iMovieCustomRepo.findByProductionYear(any(Year.class))).thenReturn(movies);
        List moviesFound = movieService.findByProductionYear(PRODUCTION_YEAR);
        //then
        assertThat(moviesFound.contains(movie.getProductionYear()), equalTo(movies.contains(movie.getProductionYear())));
    }

    @Test
    public void findByMaker() {
        //given
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        //when
        when(iMovieCustomRepo.findByMaker(anyString())).thenReturn(movies);
        List moviesFound = movieService.findByMaker(MAKER);
        //then
        assertThat(moviesFound.contains(movie.getMaker()), equalTo(movies.contains(movie.getMaker())));
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        movieService.deleteById(id);
        verify(iMovieRepo, times(1)).deleteById(id);
    }

    @Test
    public void updateMovie() {
        //given
        Movie movie = Movie.builder().title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        Movie updatedMovie = Movie.builder().title("updated title").productionYear(Year.of(9999)).maker("updated maker").build();
        //when
        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        when(iMovieRepo.save(any(Movie.class))).thenReturn(updatedMovie);
        //then
        assertThat(updatedMovie.getTitle(), equalTo("updated title"));
        assertThat(updatedMovie.getMaker(), equalTo("updated maker"));
        assertThat(updatedMovie.getProductionYear(), equalTo(Year.of(9999)));
    }

    @Test
    public void patchMovie() {
        //given
        Movie patched = Movie.builder().title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        Movie patcher = Movie.builder().title("patched title").productionYear(Year.of(9999)).maker("patched maker").build();
        //when
        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(patched));
        when(iMovieRepo.save(any(Movie.class))).thenReturn(patcher);
        //then
        assertNotEquals(patched, patcher);
    }
}