package com.example.springcashierworker;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Drink findById(long id);
}