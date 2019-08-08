package com.dominikpiotrowski.springrest.demo.api;

import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieListDataTransfer;
import com.dominikpiotrowski.springrest.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping(MovieController.BASE_URL)
public class MovieController {

    static final String BASE_URL = "/api/v1";

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/all")
    @ResponseStatus(HttpStatus.OK)
    public MovieListDataTransfer findAll() {
        List<MovieDataTransfer> allMovies = movieService.findAll();
        return new MovieListDataTransfer(allMovies);
    }

    @GetMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/movieByTitle")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer getMovieByTitle(@RequestParam String title) {
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/moviesByProduction")
    @ResponseStatus(HttpStatus.OK)
    public MovieListDataTransfer getMovieByByProductionYear(@RequestParam Year year) {
        List<MovieDataTransfer> foundMovies = movieService.getMovieByProductionYear(year);
        return new MovieListDataTransfer(foundMovies);
    }

    @GetMapping("/moviesByMaker")
    @ResponseStatus(HttpStatus.OK)
    public MovieListDataTransfer getMovieByMaker(@RequestParam String maker) {
        List<MovieDataTransfer> foundMovies = movieService.getMovieByMaker(maker);
        return new MovieListDataTransfer(foundMovies);
    }

    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody MovieDataTransfer movie) {
        movieService.addMovie(movie);
    }

    @PutMapping("/moviesUpdate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer updateMovie(@PathVariable Long id,
                                         @RequestBody MovieDataTransfer movie) {
        return movieService.updateMovie(id, movie);
    }

    @PutMapping("/moviesPatch/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer patchMovie(@PathVariable Long id, MovieDataTransfer movie) {
        return movieService.patchMovie(id, movie);
    }
}