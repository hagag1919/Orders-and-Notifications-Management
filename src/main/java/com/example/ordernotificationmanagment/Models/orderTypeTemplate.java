package com.example.ordernotificationmanagment.Models;

public abstract  class orderTypeTemplate {

    private  String productName;
    private  String recipientName;

    public orderTypeTemplate(String productName,String recipientName)
    {
        this.productName = productName;
        this.recipientName = recipientName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
