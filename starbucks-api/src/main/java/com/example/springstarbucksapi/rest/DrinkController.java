package com.example.springstarbucksapi.rest;

import com.example.springstarbucksapi.model.Drink;
import com.example.springstarbucksapi.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/* See:  https://spring.io/guides/gs/rest-service/ */

@RestController
public class DrinkController {
    @Autowired private DrinkRepository drinks;

    @GetMapping("/drink/id/{drinkid}")
    Drink getDrink(@PathVariable String drinkid, HttpServletResponse response) {
        Drink drink = drinks.findById(Long.parseLong(drinkid)) ;
        if (drink != null) {
            return drink;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drink Not Found!");
        }
    }
}