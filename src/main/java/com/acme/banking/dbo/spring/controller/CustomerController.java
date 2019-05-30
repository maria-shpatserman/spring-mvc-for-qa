package com.acme.banking.dbo.spring.controller;

import com.acme.banking.dbo.spring.Application;
import com.acme.banking.dbo.spring.domain.Account;
import com.acme.banking.dbo.spring.domain.Customer;
import org.mapstruct.Context;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/customer-api", headers = "X-API-VERSION")
public class CustomerController {

    private List<Customer> allCustomers = new ArrayList<>();
    private int nextId = 3;
    @Autowired private Logger logger;
    @Autowired
    ApplicationContext context;

    public CustomerController() {

        allCustomers.add(new Customer(1, "cust1"));
        allCustomers.add(new Customer(2, "cust2"));
    }


    @GetMapping(path = "/customers", headers = "X-API-VERSION=2")
    public List<Customer> getAllCustomers() {

        return allCustomers;
    }


    @GetMapping(path = "/customer/{i}", headers = "X-API-VERSION=2")
    public ResponseEntity<Customer> getCustomer(int i, HttpServletRequest request) {
        logger.info("Request URI= "+request.getRequestURI().toString());
        Customer cust = getCustomerById(i);
        if (cust != null) {
            return new ResponseEntity<>(cust, HttpStatus.FOUND);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found id: " + i
        );
//        context.
    }



    @DeleteMapping(path = "/deleteCustomer/{i}", headers = "X-API-VERSION=2")
    public ResponseEntity<Customer> deleteCustomer(int i) {
        Customer cust = getCustomerById(i);
        if (cust != null) {
            allCustomers.remove(cust);
            return new ResponseEntity<>(cust, HttpStatus.ACCEPTED);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found id: " + i
        );
    }


    @PostMapping(path = "/addCustomer", headers = "X-API-VERSION=2")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer cust) {

        cust.setId(nextId++);
        allCustomers.add(cust);

        return new ResponseEntity<>(cust, HttpStatus.CREATED);
    }

    private Customer getCustomerById(int i) {
        for (Customer cust : allCustomers) {
            if (cust.getId() == i) {
                return cust;
            }
        }
        return null;
    }


}
