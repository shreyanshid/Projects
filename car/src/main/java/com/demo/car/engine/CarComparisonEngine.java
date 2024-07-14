package com.demo.car.engine;

import com.demo.car.dao.CarDetailsRepository;
import com.demo.car.exception.CarBizException;
import com.demo.car.exception.DetailNotFoundException;
import com.demo.car.model.Car;
import com.demo.car.model.Cars;
import com.demo.car.util.CarConstants;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CarComparisonEngine {

    private static final Logger logger = LoggerFactory.getLogger(CarComparisonEngine.class);

    @Inject
    private CarDetailsRepository detailsRepository;

    @Transactional(TxType.REQUIRED)
    public Cars search(Car carDetails) {
        try {
            Cars cars = detailsRepository.searchCars(carDetails);
            logger.info("Found  " + cars.getCars().size() + " similar cars");
            List<Car> similarCars = cars.getCars().stream().limit(CarConstants.MAX_SIMILAR_CARS_COUNT).toList();
            return new Cars(similarCars);

        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while fetching similar car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

    @Transactional(TxType.REQUIRED)
    public Car find(int id) {
        try {
            Car car = detailsRepository.findCar(id);
            return car;
        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while finding the car detail: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

    @Transactional(TxType.REQUIRED)
    public Cars compare(int id1, int id2) {
        try {
            // Find id1 car details in parallel thread along with main thread
            CompletableFuture<Car> future1 = CompletableFuture.supplyAsync(() -> detailsRepository.findCar(id1));
            Car car2 = detailsRepository.findCar(id2);

            try {
                future1.join();
            } catch (CompletionException ex) {
                logger.error("CompletionException: " + ex.getMessage());
                throw new DetailNotFoundException(HttpStatus.NOT_FOUND.name(),
                    "Error while fetching details of car id: " + id1);
            }
            Car car1 = future1.get();

            if (car1.getModel().equals(car2.getModel())) {
                car1.setModel(null);
                car2.setModel(null);
            }
            if (car1.getBrand().equals(car2.getBrand())) {
                car1.setBrand(null);
                car2.setBrand(null);
            }
            if (car1.getEngineType().equals(car2.getEngineType())) {
                car1.setEngineType(null);
                car2.setEngineType(null);
            }
            if (car1.getColor().equals(car2.getColor())) {
                car1.setColor(null);
                car2.setColor(null);
            }
            if (car1.getNumberOfSeats() == car2.getNumberOfSeats()) {
                car1.setNumberOfSeats(0);
                car2.setNumberOfSeats(0);
            }
            if (car1.getTankSize().equals(car2.getTankSize())) {
                car1.setTankSize(null);
                car2.setTankSize(null);
            }
            if (car1.getMileage().equals(car2.getMileage())) {
                car1.setMileage(null);
                car2.setMileage(null);
            }
            if (car1.getPrice().equals(car2.getPrice())) {
                car1.setPrice(null);
                car2.setPrice(null);
            }

            return new Cars(Arrays.asList(car1, car2));

        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while finding the car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

}
