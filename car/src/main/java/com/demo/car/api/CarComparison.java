package com.demo.car.api;

import com.demo.car.model.Car;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public interface CarComparison {

    @PostMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    ResponseEntity searchCars(@RequestBody Car input);

    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseEntity getCar(@PathVariable int id);

    @GetMapping("/compare")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseEntity compareCars(@RequestParam int id1, @RequestParam int id2);

}
