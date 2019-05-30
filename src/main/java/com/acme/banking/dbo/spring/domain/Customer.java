package com.acme.banking.dbo.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@JsonPropertyOrder({ "id", "name" })
//@Entity
public class Customer {
    public void setId(long id) {
        this.id = id;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) @PositiveOrZero
    private long id;
    private String name;

    public Customer(@JsonProperty("id") long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }


    /** No-arg constructor needed by JPA */
    public Customer() { }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
