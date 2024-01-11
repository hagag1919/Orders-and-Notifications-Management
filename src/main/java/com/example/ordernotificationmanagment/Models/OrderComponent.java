package com.example.ordernotificationmanagment.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class OrderComponent
{
    private long order_id;
    private double shippingFees = 0.0;
    private double totalCost =0.0;
    private String shippingAddress , customerUserName;
    private int quantity;
    private final List<ProductPlaced> productsSerial = new ArrayList<ProductPlaced>();
    private LocalDateTime placeTime;




    public OrderComponent(long order_id, String shippingAddress, String customerUserName){
        this.order_id = order_id;
        this.shippingAddress = shippingAddress;
        this.customerUserName = customerUserName;
        this.placeTime = LocalDateTime.now();
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
    public List<ProductPlaced> getProductsSerial()
    {
        return productsSerial;
    }

    public void setProductsSerial(List<ProductPlaced> products) {
        this.productsSerial.addAll(products);
    }

    public LocalDateTime getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(LocalDateTime placeTime) {
        this.placeTime = placeTime;
    }

}


