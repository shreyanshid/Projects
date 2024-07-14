package com.demo.car.apiImpl;

import com.demo.car.api.CarComparison;
import com.demo.car.engine.CarComparisonEngine;
import com.demo.car.exception.CarBizException;
import com.demo.car.exception.DetailNotFoundException;
import com.demo.car.model.Car;
import com.demo.car.model.Cars;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CarComparisonImpl implements CarComparison {

    private static final Logger logger = LoggerFactory.getLogger(CarComparisonImpl.class);

    @Inject
    private CarComparisonEngine carComparisonEngine;

    @Override
    @Transactional(TxType.REQUIRED)
    public ResponseEntity searchCars(Car carDetails) {
        try {
            // validate request
            if (carDetails == null) {
                throw new DetailNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR.name(), "No details provided");
            }
            Cars res = carComparisonEngine.search(carDetails);
            return ResponseEntity.ok(res);
        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while fetching similar car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public ResponseEntity getCar(int id) {
        try {
            Car res = carComparisonEngine.find(id);
            return ResponseEntity.ok(res);
        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while fetching similar car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public ResponseEntity compareCars(int id1, int id2) {
        try {
            Cars res = carComparisonEngine.compare(id1, id2);
            return ResponseEntity.ok(res);
        } catch (DetailNotFoundException | CarBizException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error while fetching similar car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }
}
