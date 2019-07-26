package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.IMovieCustomRepo;
import com.dominikpiotrowski.springrest.demo.dao.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Year;
import java.util.ArrayList;
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

    public Movie findByTitle(String title) throws NoSuchMovieException {
        return iMovieCustomRepo.findByTitle(title);
    }

    public List findByProductionYear(Year year) throws NoSuchMovieException {
        return new ArrayList(iMovieCustomRepo.findByProductionYear(year));
    }

    public List findByMaker(String maker) throws NoSuchMovieException {
        return new ArrayList(iMovieCustomRepo.findByMaker(maker));
    }

    public void deleteById(Long id) {
        iMovieRepo.deleteById(id);
    }

    public Movie updateMovie(Long id, String title, Year productionYear, String maker) {

        Movie m = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        m.builder().title(title).productionYear(productionYear).maker(maker).build();
        return iMovieRepo.save(m);
    }

    public Movie patchMovie(Long id, Movie updatedMovie) {
        Movie foundMovie = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        if (foundMovie.getClass() != null) {
            if (foundMovie.getId().equals(updatedMovie.getId()) && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getTitle().equals(updatedMovie.getTitle()) && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getProductionYear().equals(updatedMovie.getProductionYear()) && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
            if (foundMovie.getMaker().equals(updatedMovie.getMaker()) && foundMovie.getId() != null) {
                iMovieRepo.save(foundMovie);
            }
        }
        return iMovieRepo.save(updatedMovie);
    }
}