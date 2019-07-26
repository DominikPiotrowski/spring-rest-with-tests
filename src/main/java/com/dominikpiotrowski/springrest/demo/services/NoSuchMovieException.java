package com.dominikpiotrowski.springrest.demo.services;

public class NoSuchMovieException extends RuntimeException {

    protected NoSuchMovieException(Throwable cause) {
        super(cause);
    }

    protected NoSuchMovieException() {
        super();
    }

    protected NoSuchMovieException(String message) {
        super(message);
    }

    protected NoSuchMovieException(String message, Throwable cause) {
        super(message, cause);
    }
}
