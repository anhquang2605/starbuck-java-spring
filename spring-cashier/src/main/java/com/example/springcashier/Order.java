package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.Random;
@Entity
@Data
@RequiredArgsConstructor
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String drink ;
    private String milk ;
    private String size ;
    private double total ;
    private String register ;
    private String status ;
    
    public static Order GetNewOrder(int drink, int milk, int size) {
        String[] DRINK_OPTIONS = { "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino" };
        String[] MILK_OPTIONS  = { "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" };
        String[] SIZE_OPTIONS  = { "Short", "Tall", "Grande", "Venti", "Your Own Cup" };
        double[][] PRICES = {
                {0, 2.95, 3.65, 3.95,3.95 },
                {0,2.25,2.65, 2.95, 2.95 },
                {0, 3.45, 4.15, 4.45, 4.45},
                {1.75, 1.95},
                {0, 2.95, 3.65, 3.95, 3.95},
        };
     	Order o = new Order() ;
    	o.drink = DRINK_OPTIONS[drink];
    	o.milk = MILK_OPTIONS[milk] ;
        if(o.drink.equalsIgnoreCase("espresso")){
            if(size > 1){
                size = 1;
            }
        } else  {
            if (size == 0) {
                size += 1;
            }
        }
        o.size = SIZE_OPTIONS[size];
    	o.status = "Ready for Payment" ;
        o.total = PRICES[drink][size];

    	return o ;
    }


}


/*

https://priceqube.com/menu-prices/%E2%98%95-starbucks

var DRINK_OPTIONS = [ "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino" ];
var MILK_OPTIONS  = [ "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" ];
var SIZE_OPTIONS  = [ "Short", "Tall", "Grande", "Venti", "Your Own Cup" ];

Caffè Latte
=============
tall 	$2.95 $3.65 $3.95 (Your Own Cup)

Caffè Americano
===============
$2.25	$2.65 $2.95 (Your Own Cup)

Caffè Mocha
=============
$3.45 $4.15 $4.45 (Your Own Cup)

Cappuccino
==========
$2.95 $3.65 $3.95 (Your Own Cup)

Espresso
========
short 	$1.75
tall 	$1.95

 */



