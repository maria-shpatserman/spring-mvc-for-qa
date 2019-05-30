package com.acme.banking.dbo.spring.controller;

import com.acme.banking.dbo.spring.dao.CustomerRepository;
import com.acme.banking.dbo.spring.domain.Customer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/customer-api", headers = "X-API-VERSION")
public class CustomerController {
    @Resource
    private CustomerRepository customers;
    @Autowired
    private Logger logger;
    @Autowired
    ApplicationContext context;

    public CustomerController() {

    }


    @GetMapping(path = "/customers", headers = "X-API-VERSION=2")
    public List<Customer> getAllCustomers() {

        return customers.findAll();
    }


    @GetMapping(path = "/customer/{i}", headers = "X-API-VERSION=2")
    public ResponseEntity<Customer> getCustomer(@PathVariable("i") long id, HttpServletRequest request) {
        logger.info("Request URI= " + request.getRequestURI().toString());
        return customers.findById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.FOUND))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found id: " + id
                ));
    }


    @DeleteMapping(path = "/deleteCustomer/{id}", headers = "X-API-VERSION=2")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        try {
            customers.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (EmptyResultDataAccessException e) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found id: " + id
            );
        }
    }


    @PostMapping(path = "/addCustomer", headers = "X-API-VERSION=2")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer cust) {

        customers.save(cust);
        return new ResponseEntity<>(cust, HttpStatus.CREATED);
    }

//    private Customer getCustomerById(int i) {
//        for (Customer cust : allCustomers) {
//            if (cust.getId() == i) {
//                return cust;
//            }
//        }
//        return null;
//    }


}
