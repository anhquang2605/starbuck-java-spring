package com.example.springcashierworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "cashier")
public class CashierDrinkWorker {

    private static final Logger log = LoggerFactory.getLogger(CashierDrinkWorker.class);

    @Autowired
    private StarbucksOrderRepository orders ;
    @Autowired
    private DrinkRepository drinks;
    @Autowired
    private ActiveOrderRepository actives;

    @RabbitHandler
    public void processCashierOrders(String id) {
        log.info( "Received  Order # " + id) ;

        // Sleeping to simulate buzy work
        try {
            Thread.sleep(60000); // 60 seconds
        } catch (Exception e) {}

        List<StarbucksOrder> list = orders.findById( Long.parseLong(id) ) ;
        List<ActiveOrder> a_list = actives.findById(Long.parseLong(id));
        if ( !list.isEmpty() && !a_list.isEmpty() ) {
            ActiveOrder active = a_list.get(0);
            Drink drink = new Drink();
            drink.setId(active.getId());
            drink.setDrink(active.getDrink());
            drink.setTotal(active.getTotal());
            drink.setSize(active.getSize());
            drink.setRegister(active.getRegister());
            drink.setMilk(active.getMilk());
            drink.setCard(active.getCard());
            drink.setStatus("FULFILLED");
            active.setStatus("FULFILLED");
            actives.save(active);
            drinks.save(drink);
            log.info( "Processed Order # " + id );
        } else {
            log.info( "[ERROR] Order # " + id + " Not Found!" );
        } 

    }
}