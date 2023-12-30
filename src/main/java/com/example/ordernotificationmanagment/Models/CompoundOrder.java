package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public class CompoundOrder extends OrderComponent
{
    private List<OrderComponent> subComponents = new ArrayList<OrderComponent>();

    public CompoundOrder(long order_id, String shippingAddress, String customerUserName, List<OrderComponent> subComponents)
    {
        super(order_id, shippingAddress, customerUserName);
        this.subComponents = subComponents;
    }
    public CompoundOrder(long order_id, String shippingAddress, String customerUserName)
    {
        super(order_id, shippingAddress, customerUserName);
    }

    public List<OrderComponent> getSubComponents()
    {
        return subComponents;
    }

//    public void setSubComponents(List<OrderComponent> orderComponent)
//    {
//        this.subComponents = orderComponent;
//    }


    public void setSubComponents(List<OrderComponent> subComponents) {
        this.subComponents = subComponents;
    }
}

// rep sup