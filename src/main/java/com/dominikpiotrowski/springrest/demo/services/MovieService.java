package com.dominikpiotrowski.springrest.demo.services;

import com.dominikpiotrowski.springrest.demo.mapper.MovieMapper;
import com.dominikpiotrowski.springrest.demo.dao.Entity.Movie;
import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.repository.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private IMovieRepo iMovieRepo;
    private MovieMapper movieMapper;

    @Autowired
    public MovieService(IMovieRepo iMovieRepo, MovieMapper movieMapper) {
        this.iMovieRepo = iMovieRepo;
        this.movieMapper = movieMapper;
    }

    public MovieDataTransfer addMovie(MovieDataTransfer movieDataTransfer) {
        Movie movie = movieMapper.movieDtoToMovie(movieDataTransfer);
        return saveAndReturnDtoMovie(movie);
    }

    private MovieDataTransfer saveAndReturnDtoMovie(Movie movie) {
        Movie savedMovie = iMovieRepo.save(movie);
        return movieMapper.movieToMovieDto(savedMovie);
    }

    public List<MovieDataTransfer> findAll() {
        List<Movie> movies = (List<Movie>) iMovieRepo.findAll();
        List<MovieDataTransfer> found = movies.stream()
                .map(movie -> movieMapper.movieToMovieDto(movie))
                .collect(Collectors.toList());

        if (found.equals(Collections.emptyList())) {
            throw new NoSuchMovieException("No such movie found.");
        } else {
            return found;
        }
    }

    public MovieDataTransfer getMovieById(Long id) {
        MovieDataTransfer found = iMovieRepo.findById(id)
                .map(movie -> movieMapper.movieToMovieDto(movie))
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));
        return found;
    }

    public MovieDataTransfer getMovieByTitle(String title) {
        MovieDataTransfer found = iMovieRepo.findByTitle(title)
                .map(movie -> movieMapper.movieToMovieDto(movie))
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));
        return found;
    }

    public List<MovieDataTransfer> getMovieByProduction(Integer year) {
        List<Movie> movies = iMovieRepo.findByProduction(year);
        List<MovieDataTransfer> found = movies.stream()
                .map(movie -> movieMapper.movieToMovieDto(movie))
                .collect(Collectors.toList());

        if (found.equals(Collections.emptyList())) {
            throw new NoSuchMovieException("No such movie found.");
        } else {
            return found;
        }
    }

    public List<MovieDataTransfer> getMovieByMaker(String maker) {
        List<Movie> movies = iMovieRepo.findByMaker(maker);
        List<MovieDataTransfer> found = movies.stream()
                .map(movie -> movieMapper.movieToMovieDto(movie))
                .collect(Collectors.toList());

        if (found.equals(Collections.emptyList())) {
            throw new NoSuchMovieException("No such movie found.");
        } else {
            return found;
        }
    }

    public void deleteById(Long id) {
        iMovieRepo.deleteById(id);
    }

    public MovieDataTransfer updateMovie(Long id, MovieDataTransfer updater) {

        Movie found = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        Movie mappedMovie = movieMapper.movieDtoToMovie(updater);

        found.setId(mappedMovie.getId());
        found.setTitle(mappedMovie.getTitle());
        found.setProduction(mappedMovie.getProduction());
        found.setMaker(mappedMovie.getMaker());

        return saveAndReturnDtoMovie(found);
    }

    public MovieDataTransfer patchMovie(Long id, MovieDataTransfer updater) {

        Movie found = iMovieRepo.findById(id)
                .orElseThrow(() -> new NoSuchMovieException("No such movie found."));

        Movie mappedMovie = movieMapper.movieDtoToMovie(updater);

        if (found.getClass() != null) {

            if (found.getMaker() != mappedMovie.getMaker() && mappedMovie.getMaker() != null) {
                found.setMaker(mappedMovie.getMaker());
                iMovieRepo.save(found);
            }
            if (found.getProduction() != mappedMovie.getProduction() && mappedMovie.getProduction() != null) {
                found.setProduction(mappedMovie.getProduction());
                iMovieRepo.save(found);
            }

            if (found.getTitle() != mappedMovie.getTitle() && mappedMovie.getTitle() != null) {
                found.setTitle(mappedMovie.getTitle());
                iMovieRepo.save(found);
            }

            if (found.getId() != mappedMovie.getId() && mappedMovie.getId() != null) {
                found.setId(mappedMovie.getId());
                iMovieRepo.save(found);
            }
        }
        return saveAndReturnDtoMovie(found);
    }
}