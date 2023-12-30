package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.Customer;

public class AccountManagement implements IAccountManagement
{

    private final Db db = Db.getInstance();

    @Override
    public String register(Customer customer)
    {
        //Check If Customer Already Registered
        for(int i = 0 ; i < db.getCustomers().size(); i++)
        {
            if(db.getCustomers().get(i).getUserName().equals(customer.getUserName()))
            {
                return "You Are Already Registered";
            }
        }

        //Creating Account For Customer
        db.getCustomers().add(customer);
        return "Registered Successfully";

    }

    @Override
    public Customer signIn(String userName, String password)
    {
        //Check Credentials OF Customer
        for(int i = 0 ; i < db.getCustomers().size(); i++)
        {
            if(db.getCustomers().get(i).getUserName().equals(userName) && db.getCustomers().get(i).getPassword().equals(password))
            {
                return db.getCustomers().get(i);
            }
        }
        //Customer Isnot Exisit
        return null;

    }



}