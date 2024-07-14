package com.demo.car.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class Car {

    private int id;
    private String model;
    private String brand;
    private String engineType;
    private String color;
    private int numberOfSeats;
    private String tankSize;
    private String mileage;
    private String price;

    public Car() {
    }

    public Car(int id, String model, String brand, String engineType, String color, int numberOfSeats, String tankSize,
        String mileage, String price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.engineType = engineType;
        this.color = color;
        this.numberOfSeats = numberOfSeats;
        this.tankSize = tankSize;
        this.mileage = mileage;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getTankSize() {
        return tankSize;
    }

    public void setTankSize(String tankSize) {
        this.tankSize = tankSize;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", model='" + model + '\'' +
            ", brand='" + brand + '\'' +
            ", engineType='" + engineType + '\'' +
            ", color='" + color + '\'' +
            ", numberOfSeats=" + numberOfSeats +
            ", tankSize='" + tankSize + '\'' +
            ", mileage='" + mileage + '\'' +
            ", price='" + price + '\'' +
            '}';
    }

    public enum COLUMN_NAMES {
        ID,
        MODEL,
        BRAND,
        ENGINE_TYPE,
        COLOR,
        NO_OF_SEATS,
        TANK_SIZE,
        MILEAGE,
        PRICE
    }

}
