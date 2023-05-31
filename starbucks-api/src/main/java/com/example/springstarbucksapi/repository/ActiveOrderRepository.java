package com.example.springstarbucksapi.repository;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import com.example.springstarbucksapi.model.ActiveOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveOrderRepository extends JpaRepository<ActiveOrder, Long> {

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
    // https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories.query-methods.query-creation

    ActiveOrder findByRegister(String register);
    long deleteByRegister(String register);
}