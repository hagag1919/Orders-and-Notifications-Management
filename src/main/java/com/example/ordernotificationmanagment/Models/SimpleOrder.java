package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrder extends OrderComponent
{
    private final List<Product> products = new ArrayList<Product>();

    public SimpleOrder(long order_id, double shippingFees, double totalCost, String shippingAddress, String customerUserName, int quantity)
    {
        super(order_id, shippingFees, totalCost, shippingAddress, customerUserName, quantity);

    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void addProduct(Product product)
    {
        this.products.add(product);
    }



}
