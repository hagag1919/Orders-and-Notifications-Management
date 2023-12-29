package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Models.Customer;

public interface IAccountManagement
{
    public String register(Customer customer);

    public Customer signIn(String userName , String password);


}