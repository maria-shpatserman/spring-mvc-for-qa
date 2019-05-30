package com.acme.banking.dbo.spring.controller;

import com.acme.banking.dbo.spring.domain.Account;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(path = "/super-api", headers = "X-API-VERSION")
public class SuperAccountController {

    @GetMapping(path = "/accounts", headers = "X-API-VERSION=2")
    public String getAllAccounts() {


        return "Hello world!";
    }
}
