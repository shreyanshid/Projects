package com.demo.car.dao;

import com.demo.car.exception.CarBizException;
import com.demo.car.exception.DetailNotFoundException;
import com.demo.car.model.Car;
import com.demo.car.model.Car.COLUMN_NAMES;
import com.demo.car.model.Cars;
import com.demo.car.util.CarQueries;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarDetailsRepository {

    private static final Logger logger = LoggerFactory.getLogger(CarDetailsRepository.class);

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Transactional(TxType.REQUIRED)
    public Cars searchCars(Car car) {
        try {
            int mil = Integer.parseInt(car.getMileage());
            int milL = mil - 5;
            int milR = mil + 5;

            int noOfSeats = car.getNumberOfSeats();
            int noOfSeatsL = noOfSeats - 2;
            int noOfSeatsR = noOfSeatsL + 2;

            int tankSize = Integer.parseInt(car.getTankSize());
            int tankSizeL = tankSize - 5;
            int tankSizeR = tankSize + 5;

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("brand", car.getBrand());
            parameters.addValue("engineType", car.getEngineType());
            parameters.addValue("noOfSeatsL", noOfSeatsL);
            parameters.addValue("noOfSeatsR", noOfSeatsR);
            parameters.addValue("tankSizeL", tankSizeL);
            parameters.addValue("tankSizeR", tankSizeR);
            parameters.addValue("mileageL", milL);
            parameters.addValue("mileageR", milR);

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            List<Car> res = namedParameterJdbcTemplate.query(CarQueries.SEARCH_BY_DETAILS, parameters,
                (resultSet, i) -> {
                    Car obj = new Car();
                    obj.setId(resultSet.getInt(COLUMN_NAMES.ID.name()));
                    obj.setModel(resultSet.getString(COLUMN_NAMES.MODEL.name()));
                    obj.setBrand(resultSet.getString(COLUMN_NAMES.BRAND.name()));
                    obj.setEngineType(resultSet.getString(COLUMN_NAMES.ENGINE_TYPE.name()));
                    obj.setColor(resultSet.getString(COLUMN_NAMES.COLOR.name()));
                    obj.setNumberOfSeats(resultSet.getInt(COLUMN_NAMES.NO_OF_SEATS.name()));
                    obj.setTankSize(resultSet.getString(COLUMN_NAMES.TANK_SIZE.name()));
                    obj.setMileage(resultSet.getString(COLUMN_NAMES.MILEAGE.name()));
                    obj.setPrice(resultSet.getString(COLUMN_NAMES.PRICE.name()));
                    return obj;
                });

            if (res.isEmpty()) {
                logger.error("No other cars found for provided queries");
                throw new DetailNotFoundException(HttpStatus.NOT_FOUND.name(),
                    "No other cars found for provided queries");
            }
            return new Cars(res);
        } catch (DetailNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching similar car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }

    @Transactional(TxType.REQUIRED)
    public Car findCar(int id) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", id);
            logger.info("Car Id: " + id);

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            List<Car> res = namedParameterJdbcTemplate.query(CarQueries.FIND_BY_ID, parameters,
                (resultSet, i) -> {
                    Car obj = new Car();
                    obj.setId(resultSet.getInt(COLUMN_NAMES.ID.name()));
                    obj.setModel(resultSet.getString(COLUMN_NAMES.MODEL.name()));
                    obj.setBrand(resultSet.getString(COLUMN_NAMES.BRAND.name()));
                    obj.setEngineType(resultSet.getString(COLUMN_NAMES.ENGINE_TYPE.name()));
                    obj.setColor(resultSet.getString(COLUMN_NAMES.COLOR.name()));
                    obj.setNumberOfSeats(resultSet.getInt(COLUMN_NAMES.NO_OF_SEATS.name()));
                    obj.setTankSize(resultSet.getString(COLUMN_NAMES.TANK_SIZE.name()));
                    obj.setMileage(resultSet.getString(COLUMN_NAMES.MILEAGE.name()));
                    obj.setPrice(resultSet.getString(COLUMN_NAMES.PRICE.name()));
                    return obj;
                });

            if (res.isEmpty()) {
                logger.error("No cars found for give id");
                throw new DetailNotFoundException(HttpStatus.NOT_FOUND.name(), "No cars found for give id");
            }
            return res.get(0);
        } catch (DetailNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while finding car details: " + e.getMessage());
            logger.error("Stack trace: " + e.getStackTrace());
            throw new CarBizException(e.getMessage());
        }
    }
}
