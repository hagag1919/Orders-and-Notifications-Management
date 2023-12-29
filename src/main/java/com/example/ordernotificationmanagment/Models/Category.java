package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public class Category
{
    private int id;
    private String name;
    private ArrayList<Product> products = new ArrayList<Product>();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(Product product)
    {
        this.products.add(product);
    }

    public void removeProducts(Product product)
    {
        this.products.remove(product);
    }

    public int getRemaning()
    {
        return this.products.size();
    }
}
