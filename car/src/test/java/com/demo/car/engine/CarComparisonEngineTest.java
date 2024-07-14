package com.demo.car.engine;

import com.demo.car.dao.CarDetailsRepository;
import com.demo.car.model.Car;
import com.demo.car.model.Cars;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarComparisonEngineTest {

    @InjectMocks
    private CarComparisonEngine comparisonEngine;

    @Mock
    private CarDetailsRepository detailsRepository;

    private List<Car> cars;
    private Car car;

    @Before
    public void init() {
        cars = new ArrayList<>();
        Car car1 = new Car(1, "TIAGO", "TATA", "PETROL", "RED", 5, "60", "19", "700000");
        car = car1;
        cars.add(car1);
    }

    @Test
    public void testSearchCars() {
        Mockito.when(detailsRepository.searchCars(Mockito.any())).thenReturn(new Cars(cars));
        Cars res = comparisonEngine.search(new Car());
        Assert.assertNotNull(res);
        Assert.assertEquals(1, res.getCars().size());
        Assert.assertEquals(cars.get(0), res.getCars().get(0));
    }

    @Test
    public void testGetCar() {
        Mockito.when(detailsRepository.findCar(1)).thenReturn(car);
        Car res = comparisonEngine.find(1);
        Assert.assertNotNull(res);
        Assert.assertEquals(1, res.getId());
        Assert.assertEquals(car, res);
    }

    @Test
    public void testCompareCar() {
        Car car2 = new Car(2, "TIAGO", "TATA", "PETROL", "WHITE", 5, "60", "20", "700000");
        Mockito.when(detailsRepository.findCar(1)).thenReturn(car);
        Mockito.when(detailsRepository.findCar(2)).thenReturn(car2);
        Cars res = comparisonEngine.compare(1, 2);
        Assert.assertNotNull(res);
        Assert.assertEquals(2, res.getCars().size());
        Assert.assertNull(res.getCars().get(0).getEngineType());
        Assert.assertNull(res.getCars().get(1).getEngineType());
    }

}
