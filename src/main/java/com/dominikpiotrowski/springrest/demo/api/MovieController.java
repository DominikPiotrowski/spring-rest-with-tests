package com.dominikpiotrowski.springrest.demo.api;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(MovieController.BASE_URL)
public class MovieController {

    public static final String BASE_URL = "/api/movies";

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.FOUND)
    public List findAll() {
        List<Movie> allMovies = movieService.findAll();
        return new ArrayList(allMovies);
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public Movie findById(@RequestParam Long id) {
        return movieService.findById(id);
    }

    @GetMapping("/findByTitle")
    @ResponseStatus(HttpStatus.FOUND)
    public Movie findByTitle(@RequestParam String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping("/findByProductionYear")
    @ResponseStatus(HttpStatus.FOUND)
    public List findByProductionYear(@RequestParam Year year) {
        List<Movie> moviesWithGivenProductionYear = movieService.findByProductionYear(year);
        return new ArrayList(moviesWithGivenProductionYear);
    }
    @GetMapping("/findByMaker")
    @ResponseStatus(HttpStatus.FOUND)
    public List findByMaker(@RequestParam String maker) {
        List<Movie> moviesWithGivenMaker = movieService.findByMaker(maker);
        return new ArrayList(moviesWithGivenMaker);
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