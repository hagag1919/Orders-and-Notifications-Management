package com.example.ordernotificationmanagment.Models;

public class ProductPlaced {

    private  String productSerial;
    private int quantity;

    public ProductPlaced(String productSerial, int quantity) {
        this.productSerial = productSerial;
        this.quantity = quantity;
    }

    public void setProductSerial(String productSerial) {
        this.productSerial = productSerial;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductSerial() {
        return productSerial;
    }

    public int getQuantity() {
        return quantity;
    }
}
