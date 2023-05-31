package com.example.springstarbucksapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVE_ORDER")
@Data
@RequiredArgsConstructor
public class ActiveOrder {

    @Id
    private Long id;
    @Column(nullable = false)
    private String drink;
    @Column(nullable = false)
    private String milk;
    @Column(nullable = false)
    private String size;
    private double total;
    private String status;
    private String register;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @JsonIgnore  /* https://www.baeldung.com/jackson-ignore-properties-on-serialization */
    private StarbucksCard card;


}