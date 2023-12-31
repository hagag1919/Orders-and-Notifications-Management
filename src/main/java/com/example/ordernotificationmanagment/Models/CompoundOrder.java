package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public class CompoundOrder extends OrderComponent
{
    private List<SimpleOrder> subComponents = new ArrayList<SimpleOrder>();

    public CompoundOrder(long order_id, String shippingAddress, String customerUserName, List<SimpleOrder> subComponents)
    {
        super(order_id, shippingAddress, customerUserName);
        this.subComponents = subComponents;
    }


    public List<SimpleOrder> getSubComponents()
    {
        return subComponents;
    }




    public void setSubComponents(List<SimpleOrder> subComponents) {
        this.subComponents = subComponents;
    }
}

// rep sup