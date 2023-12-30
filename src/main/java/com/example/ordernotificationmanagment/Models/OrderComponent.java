package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderComponent
{
    private long order_id;
    private double shippingFees ,totalCost;
    private String shippingAddress , customerUserName;
    private int quantity;
    private final List<Product> products = new ArrayList<Product>();

    public OrderComponent() {}

    public OrderComponent(long order_id, double shippingFees, double totalCost, String shippingAddress, String customerUserName, int quantity)
    {
        this.order_id = order_id;
        this.shippingFees = shippingFees;
        this.totalCost = totalCost;
       this.shippingAddress = shippingAddress;
        this.customerUserName = customerUserName;
        this.quantity = quantity;
    }

    public OrderComponent(long order_id, String shippingAddress, String customerUserName){
        this.order_id = order_id;
        this.shippingAddress = shippingAddress;
        this.customerUserName = customerUserName;
    }

    public long getOrder_id()
    {
        return order_id;
    }


    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products.addAll(products);
    }
    public void addProduct(Product product)
    {
        this.products.add(product);
    }

}
