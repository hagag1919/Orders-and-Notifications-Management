package com.example.ordernotificationmanagment.Models;

public class Product
{
    private String name,serialNumber,vendor;
    private double price;
    private int category_Id;
    private int reminder = 0;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getCategory_Id()
    {
        return category_Id;
    }

    public void setCategory_Id(int category_Id)
    {
        this.category_Id = category_Id;
    }

    public int getReminder()
    {
        return reminder;
    }

    public void setReminder(int reminder)
    {
        this.reminder = reminder;
    }
}
