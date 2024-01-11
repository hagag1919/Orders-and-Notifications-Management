package com.example.ordernotificationmanagment.Models;

public class shipmentType extends orderTypeTemplate{

    private String shipCompanyName;

    public shipmentType(String productName, String recipientName, String shipCompanyName) {
        super(productName, recipientName);
        this.shipCompanyName = shipCompanyName;
    }

    public void setShipCompanyName(String shipCompanyName) {
        this.shipCompanyName = shipCompanyName;
    }

    public String getShipCompanyName() {
        return shipCompanyName;
    }
}
