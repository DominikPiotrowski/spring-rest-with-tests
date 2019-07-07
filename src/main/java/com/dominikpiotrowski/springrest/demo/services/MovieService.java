package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class MovieService {

    private IMovieRepo iMovieRepo;
    private IMovieCustomRepo iMovieCustomRepo;

    @Autowired
    public MovieService(IMovieRepo iMovieRepo, IMovieCustomRepo iMovieCustomRepo) {
        this.iMovieRepo = iMovieRepo;
        this.iMovieCustomRepo = iMovieCustomRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addMovies() {
        addMovie(new Movie(1L, "Terminator", Year.of(1984), "Cameron"));
        addMovie(new Movie(2L, "Space Oddysey", Year.of(1968), "Cubrick"));
        addMovie(new Movie(3L, "Watchmen", Year.of(2009), "Snyder"));
        addMovie(new Movie(4L, "Star Trek", Year.of(1979), "Wise"));
    }

    public void addMovie(Movie movie) {
        iMovieRepo.save(movie);
    }

    public List<Movie> findAll() {
        return (List<Movie>) iMovieRepo.findAll();
    }

    public Movie findById(Long id) {
        return iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));
    }

    public Movie findByTitle(String title) {
        return iMovieCustomRepo.findByTitle(title)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));
    }

    public List findByProductionYear(Year year) {
        return iMovieCustomRepo.findByProductionYear(year);
    }

    public List findByMaker(String maker) {
        return iMovieCustomRepo.findByMaker(maker);
    }

    public void deleteById(Long id) {
        iMovieRepo.deleteById(id);
    }

    public Movie updateMovie(Long id, String title, Year productionYear, String maker) {

        Movie m = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        m.setTitle(title);
        m.setProductionYear(productionYear);
        m.setMaker(maker);

        m.builder().build();
        return iMovieRepo.save(m);
    }

    public Movie patchMovie(Long id, Movie updatedMovie) {
        Movie foundMovie = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        if (foundMovie.getClass() != null) {
            if (foundMovie.getId() != updatedMovie.getId() && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getTitle() != updatedMovie.getTitle() && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getProductionYear() != updatedMovie.getProductionYear() && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getMaker() != updatedMovie.getMaker() && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
        }
        return iMovieRepo.save(updatedMovie);
    }
}