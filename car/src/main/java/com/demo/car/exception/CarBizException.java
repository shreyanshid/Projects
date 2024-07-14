package com.demo.car.exception;

public class CarBizException extends RuntimeException {

    public CarBizException(String errorMessage) {
        super(errorMessage);
    }

}
