package com.example.springcashier;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Command {

    private String action ;
    private String message ;
    private String stores ;
    private int drinks;
    private int sizes;
    private int milks;
    private String register ;
    private Boolean cleared;
    private int drink;
    private int size;
    private double total;
    private String status;
    private int milk;
}



