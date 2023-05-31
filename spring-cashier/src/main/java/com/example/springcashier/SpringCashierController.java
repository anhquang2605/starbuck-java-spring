package com.example.springcashier;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
@Controller
@RequestMapping("/user/starbucks")
public class SpringCashierController {
    @Autowired
    private Queue queue;
    @Autowired
    private RabbitTemplate rabbit;
    @Value("${starbucks.client.apikey}") String API_KEY ;
    @Value("${starbucks.client.apihost}") String API_HOST ;
    @Value("${starbucks.client.register}") String REGISTER ;
    public HashMap<String, Integer> drinkMap;
    public HashMap<String, Integer> sizeMap;
    public HashMap<String, Integer> milkMap;

    public SpringCashierController(){
        drinkMap = new HashMap<String, Integer>();//Creating HashMap
        sizeMap = new HashMap<String, Integer>();
        milkMap = new HashMap<String, Integer>();
        drinkMap.put("Caffe Latte",0);  //Put elements in Map
        drinkMap.put("Caffe Americano",1);
        drinkMap.put("Caffe Mocha",2);
        drinkMap.put("Espresso",3);
        drinkMap.put("Cappuccino",4);
        milkMap.put("Whole Milk",0);
        milkMap.put("2% Milk",1);
        milkMap.put("Nonfat Milk",2);
        milkMap.put("Almond Milk",3);
        milkMap.put("Soy Milk",4);
        sizeMap.put("Short",0);
        sizeMap.put("Tall",1);
        sizeMap.put("Grande",2);
        sizeMap.put("Venti",3);
        sizeMap.put("Your Own Cup",4);
    }
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping
    public String getAction( @ModelAttribute("command") Command command, 
                            Model model, HttpSession session) {

        String message = "" ;

        command.setRegister( "5012349" ) ;
        command.setCleared(true);
        command.setDrink(0);
        command.setSize(0);
        command.setMilk(0);
        message = "Starbucks Reserved Order" + "\n\n" +
            "Register: " + command.getRegister() + "\n" +
            "Status:   " + "Ready for New Order"+ "\n" ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;

    }


    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "" ;
        String message = "";

        log.info( "Action: " + action ) ;
        command.setRegister( command.getStores() ) ;
        command.setDrink(command.getDrinks());
        command.setSize(command.getSizes());
        command.setMilk(command.getMilks());

        log.info( "Command: " + command );
        /* Process Post Action */
        if ( action.equals("Place Order") ) {
            if(command.getCleared() == false){
                message = "Please clear an order first before placing new one";
            } else {
                Order order = Order.GetNewOrder(command.getDrink(), command.getMilk(), command.getSize()) ;
                order.setRegister( command.getRegister() ) ;
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Drink: " + order.getDrink() + "\n" +
                        "Milk:  " + order.getMilk() + "\n" +
                        "Size:  " + order.getSize() + "\n" +
                        "Total: $ " + order.getTotal() + "\n" +
                        "\n" +
                        "Register: " + order.getRegister() + "\n" +
                        "Status:   " + order.getStatus() + "\n" ;
                command.setDrink(drinkMap.get(order.getDrink()));
                command.setRegister(order.getRegister());
                command.setSize(sizeMap.get(order.getSize()));
                command.setTotal(order.getTotal());
                command.setStatus(order.getStatus());
                command.setMilk(milkMap.get(order.getMilk()));
                command.setCleared(false);//require to clear again before we can place a new order again
                System.out.println(order);
                //make api call to create new order, wait for payment in the mobile app
                //message = "";
                //resourceUrl = "http://localhost:8080/order/register/"+command.getRegister();//within post action
                resourceUrl = "http://"+API_HOST+"/order/register/"+command.getRegister()+"?apikey="+API_KEY;
                // get response as POJO
                try{
                    HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(order) ;
                    ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
                    Order newOrder = newOrderResponse.getBody();
                    System.out.println( newOrder );
                }catch(Exception e){
                    message="Something wrong, please try again";
                }

                // pretty print JSON

            }
        }
        else if ( action.equals("Get Order") ) {//make call to the getorder endpoint, display the status
            if(command.getTotal() != 0){
                //make api call to the new order endpoint
                message = "";
                resourceUrl = "http://" + API_HOST +"/order/register/"+command.getRegister()+"?apikey=" + API_KEY;
                System.out.println(resourceUrl);
                ResponseEntity<Order> orderResponse = restTemplate.getForEntity(resourceUrl, Order.class);
                Order orderMsg = orderResponse.getBody();
                System.out.println(orderMsg.getTotal());
                message = "Order retrieved" + "\n\n" +
                        "Drink: " + orderMsg.getDrink() + "\n" +
                        "Milk:  " + orderMsg.getMilk() + "\n" +
                        "Size:  " + orderMsg.getSize() + "\n" +
                        "Total: $ " + orderMsg.getTotal() + "\n" +
                        "\n" +
                        "Register: " + orderMsg.getRegister() + "\n" +
                        "Status:   " + orderMsg.getStatus() + "\n" ;
                String status = orderMsg.getStatus();
                //Keep checking if the status of order is paid, if paid then send to queue
                if(status.toLowerCase().contains("paid")){
                    System.out.println("Order paid, sending to queue worker");
                    rabbit.convertAndSend(queue.getName(), orderMsg.getId() + "");
                }
            }else{
                message = "Please place order first before getting one";
            }

        }
        else if ( action.equals("Clear Order") ) {
            //check if there is an active order by callking orders endpoint
            if(command.getTotal() != 0){

                try{
                    resourceUrl = "http://" + API_HOST +"/order/register/"+command.getRegister()+"?apikey=" + API_KEY;
                    restTemplate.delete(resourceUrl);
                    command.setDrink(0);
                    command.setMilk(0);
                    command.setStatus("");
                    command.setTotal(0);
                    command.setSize(0);
                    message = "";
                    message = "Starbucks Cleared an Active Order" + "\n\n" +
                            "For register: " + command.getRegister() + "\n" +
                            "Status:   " + "Ready for New Order"+ "\n" ;
                    command.setCleared(true);
                }catch(Exception e){
                    message = "Could not clear order, there could be no order to be cleared";
                }
            } else {
                message ="No order placed yet";
            }


            //make api call to the clear order endpoint,

        }         
        command.setMessage( message ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;

    }
    

}

