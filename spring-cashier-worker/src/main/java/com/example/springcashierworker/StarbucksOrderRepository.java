package com.example.springcashierworker;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import com.example.springcashierworker.StarbucksOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StarbucksOrderRepository extends CrudRepository<StarbucksOrder, Long> {
    List<StarbucksOrder> findById(long id) ;
}


