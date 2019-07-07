package com.dominikpiotrowski.springrest.demo.services;

public class NoSuchMovieException extends RuntimeException{

    public NoSuchMovieException(String message) {
        super(message);
    }
}
