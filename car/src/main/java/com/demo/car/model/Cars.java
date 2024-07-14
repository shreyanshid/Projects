package com.demo.car.model;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cars {

    private List<Car> cars;

    public Cars(List<Car> details) {
        cars = new ArrayList<>();
        cars.addAll(details);
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
