package com.demo.car.exception;

public class DetailNotFoundException extends RuntimeException {

    public DetailNotFoundException(String statusCode, String errorMessage) {
        super(errorMessage, new Throwable(statusCode));
    }

}
