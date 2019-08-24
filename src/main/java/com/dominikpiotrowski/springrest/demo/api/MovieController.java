package com.dominikpiotrowski.springrest.demo.api;

import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieListDataTransfer;
import com.dominikpiotrowski.springrest.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/movieByTitle/{title}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/moviesByProduction/{production}")
    @ResponseStatus(HttpStatus.OK)
    public MovieListDataTransfer getMovieByByProduction(@PathVariable Integer production) {
        List<MovieDataTransfer> foundMovies = movieService.getMovieByProduction(production);
        return new MovieListDataTransfer(foundMovies);
    }

    @GetMapping("/moviesByMaker/{maker}")
    @ResponseStatus(HttpStatus.OK)
    public MovieListDataTransfer getMovieByMaker(@PathVariable String maker) {
        List<MovieDataTransfer> foundMovies = movieService.getMovieByMaker(maker);
        return new MovieListDataTransfer(foundMovies);
    }

    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDataTransfer addMovie(@RequestBody MovieDataTransfer movie) {
        return movieService.addMovie(movie);
    }

    @PutMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer updateMovie(@PathVariable Long id,
                                         @RequestBody MovieDataTransfer movie) {
        return movieService.updateMovie(id, movie);
    }

    @PatchMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDataTransfer patchMovie(@PathVariable Long id,
                                        @RequestBody MovieDataTransfer movie) {
        return movieService.patchMovie(id, movie);
    }
}