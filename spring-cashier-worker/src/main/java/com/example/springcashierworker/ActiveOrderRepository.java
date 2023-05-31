package com.example.springcashierworker;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ActiveOrderRepository extends JpaRepository<ActiveOrder, Long> {
    List<ActiveOrder> findById(long id) ;
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
    // https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories.query-methods.query-creation
}