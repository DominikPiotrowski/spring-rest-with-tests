package com.dominikpiotrowski.springrest.demo.api;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieApi {

    private MovieService movieService;

    @Autowired
    public MovieApi(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.FOUND)
    public Iterable<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Movie> findById(@RequestParam Long id) {
        return Optional.ofNullable(movieService.findById(id));
    }

    @GetMapping("/findByTitle")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Movie> findByTitle(@RequestParam String title) {
        return Optional.ofNullable(movieService.findByTitle(title));
    }

    @GetMapping("/findByProductionYear")
    @ResponseStatus(HttpStatus.FOUND)
    public Iterable<Movie> findByProductionYear(@RequestParam Year year) {
        return movieService.findByProductionYear(year);
    }
    @GetMapping("/findByMaker")
    @ResponseStatus(HttpStatus.FOUND)
    public Iterable<Movie> findByMaker(@RequestParam String maker) {
        return movieService.findByMaker(maker);
    }

    @DeleteMapping("/deleteById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@RequestParam Long id) {
        movieService.deleteById(id);
    }

    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestParam Movie movie) {
        movieService.addMovie(movie);
    }

    @PostMapping("/updateMovie")
    @ResponseStatus(HttpStatus.OK)
    public Movie updateMovie(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam Year year,
                             @RequestParam String maker) {
        return movieService.updateMovie(id, title, year, maker);
    }

    @PutMapping("/patchMovie")
    @ResponseStatus(HttpStatus.OK)
    public Movie patchMovie(@PathVariable Long id, Movie movie) {
        return movieService.patchMovie(id, movie);
    }
}