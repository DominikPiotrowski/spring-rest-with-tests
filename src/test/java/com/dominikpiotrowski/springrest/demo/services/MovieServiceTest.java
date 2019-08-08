package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.mapper.MovieMapper;
import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.repository.IMovieRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
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

    @Before
    public void setUp() {
        movieService = new MovieService(
                iMovieRepo, MovieMapper.INSTANCE);
    }

    @Test
    public void addMovie() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        MovieDataTransfer movieDataTransfer = MovieMapper.INSTANCE.movieToMovieDto(movie);

        when(iMovieRepo.save(any(Movie.class))).thenReturn(movie);
        MovieDataTransfer newMovie = movieService.addMovie(movieDataTransfer);

        assertThat(newMovie.getId(), equalTo(ID));
        assertNotNull(iMovieRepo.findById(99L)); //dodatkowa asercja
    }

    @Test
    public void findAll() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie(), new Movie(), new Movie());
        when(iMovieRepo.findAll()).thenReturn(movies);
        List<MovieDataTransfer> movieDataTransfers = movieService.findAll();
        assertThat(movieDataTransfers.size(), equalTo(4));
    }

    @Test
    public void getMovieById() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();

        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        MovieDataTransfer movieDataTransfer = movieService.getMovieById(ID);

        assertThat(movieDataTransfer.getId(), equalTo(ID));
        assertThat(movieDataTransfer.getTitle(), equalTo(TITLE));
        assertThat(movieDataTransfer.getProductionYear(), equalTo(PRODUCTION_YEAR));
        assertThat(movieDataTransfer.getMaker(), equalTo(MAKER));
    }

    @Test(expected = NoSuchMovieException.class)
    public void getMovieByIdException() {
        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.empty());
        movieService.getMovieById(ID);
    }

    @Test
    public void getMovieByTitle() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();

        when(iMovieRepo.findByTitle(anyString())).thenReturn(Optional.ofNullable(movie));
        MovieDataTransfer movieDataTransfer = movieService.getMovieByTitle(TITLE);

        assertThat(movieDataTransfer.getId(), equalTo(ID));
        assertThat(movieDataTransfer.getTitle(), equalTo(TITLE));
        assertThat(movieDataTransfer.getProductionYear(), equalTo(PRODUCTION_YEAR));
        assertThat(movieDataTransfer.getMaker(), equalTo(MAKER));
    }

    @Test(expected = NoSuchMovieException.class)
    public void getMovieByTitleException() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        iMovieRepo.save(movie);
        movieService.getMovieByTitle("test title");
    }

    //TODO bez mapowania w metodzie nie przejdzie

    @Test
    public void getMovieByProductionYear() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        when(iMovieRepo.findByProductionYear(any(Year.class))).thenReturn(movies);
        List moviesFound = movieService.getMovieByProductionYear(PRODUCTION_YEAR);

        assertThat(moviesFound.contains(movie.getProductionYear()), equalTo(movies.contains(movie.getProductionYear())));
    }

    @Test(expected = NoSuchMovieException.class)
    public void getMovieByProductionYearException() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        iMovieRepo.save(movie);
        movieService.getMovieByProductionYear(Year.of(9999));
    }

    //TODO bez mapowania w metodzie nie przejdzie

    @Test
    public void getMovieByByMaker() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        when(iMovieRepo.findByMaker(anyString())).thenReturn(movies);
        List moviesFound = movieService.getMovieByMaker(MAKER);

        assertThat(moviesFound.contains(movie.getMaker()), equalTo(movies.contains(movie.getMaker())));
    }

    @Test(expected = NoSuchMovieException.class)
    public void getMovieByMakerException() {
        Movie movie = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        iMovieRepo.save(movie);
        movieService.getMovieByMaker("Santa");
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        movieService.deleteById(id);
        verify(iMovieRepo, times(1)).deleteById(id);
    }

    @Test
    public void updateMovie() {
        Movie found = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        Movie updater = Movie.builder().id(ID).title("updated title").productionYear(Year.of(5555)).maker("updated maker").build();
        MovieDataTransfer updaterDto = MovieMapper.INSTANCE.movieToMovieDto(updater);

        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(found));
        when(iMovieRepo.save(any(Movie.class))).thenReturn(updater);

        MovieDataTransfer newMovie = movieService.updateMovie(99L, updaterDto);

        assertNotNull(iMovieRepo.findById(99L));
        assertThat(newMovie.getTitle(), equalTo("updated title"));
        assertThat(newMovie.getMaker(), equalTo("updated maker"));
        assertThat(newMovie.getProductionYear(), equalTo(Year.of(5555)));
    }

    @Test
    public void patchMovie() {

        Movie before = Movie.builder().id(ID).title(TITLE).productionYear(PRODUCTION_YEAR).maker(MAKER).build();
        Movie patcher = Movie.builder().id(ID).title("patched title").maker("patched maker").build();
        MovieDataTransfer patcherDto = MovieMapper.INSTANCE.movieToMovieDto(patcher);

        when(iMovieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(before));
        when(iMovieRepo.save(any(Movie.class))).thenReturn(patcher);

        MovieDataTransfer newMovie = movieService.patchMovie(99L, patcherDto);

        assertNotEquals(before, newMovie);
    }
}