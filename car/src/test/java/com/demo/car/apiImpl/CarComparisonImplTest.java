package com.demo.car.apiImpl;

import com.demo.car.engine.CarComparisonEngine;
import com.demo.car.exception.DetailNotFoundException;
import com.demo.car.model.Car;
import com.demo.car.model.Cars;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarComparisonImplTest {

    @InjectMocks
    private CarComparisonImpl comparisonImpl;

    @Mock
    private CarComparisonEngine comparisonEngine;

    private Car car;

    @Before
    public void init() {
        car = new Car(1, "TIAGO", "TATA", "PETROL", "RED", 5, "60", "19", "700000");
    }

    @Test
    public void testSearchCars() {
        Mockito.when(comparisonEngine.search(Mockito.any())).thenReturn(new Cars(Arrays.asList(car)));
        ResponseEntity res = comparisonImpl.searchCars(new Car());
        Assert.assertNotNull(res);
        Assert.assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
    }

    @Test(expected = DetailNotFoundException.class)
    public void testSearchCarsException() {
        comparisonImpl.searchCars(null);
    }

    @Test
    public void testGetCar() {
        Mockito.when(comparisonEngine.find(1)).thenReturn(car);
        ResponseEntity res = comparisonImpl.getCar(1);
        Assert.assertNotNull(res);
        Assert.assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
    }

    @Test
    public void testCompareCar() {
        Car car2 = new Car(2, "TIAGO", "TATA", "PETROL", "WHITE", 5, "60", "20", "700000");
        Mockito.when(comparisonEngine.compare(1, 2)).thenReturn(new Cars(Arrays.asList(car, car2)));
        ResponseEntity res = comparisonImpl.compareCars(1, 2);
        Assert.assertNotNull(res);
        Assert.assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        Assert.assertNotNull(res.getBody());
    }

}
