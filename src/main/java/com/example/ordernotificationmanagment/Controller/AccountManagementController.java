package com.example.ordernotificationmanagment.Controller;

import com.example.ordernotificationmanagment.Models.Customer;
import com.example.ordernotificationmanagment.Models.LoginRequest;
import com.example.ordernotificationmanagment.Services.AccountManagement;
import com.example.ordernotificationmanagment.Services.IAccountManagement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/am_api")
public class AccountManagementController {

    private final IAccountManagement accountManagement = new AccountManagement();


    @PostMapping(value = "/customer/register")
    public String register(@RequestBody Customer customer) {
        return this.accountManagement.register(customer);
    }

    @GetMapping(value = "/customer/signin")
    public Customer signIn(@RequestBody LoginRequest loginRequest) {
        return this.accountManagement.signIn(loginRequest.getUserName(), loginRequest.getPassword());

    }

}
