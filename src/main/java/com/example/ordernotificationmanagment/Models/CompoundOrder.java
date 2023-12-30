package com.example.ordernotificationmanagment.Models;

import java.util.ArrayList;
import java.util.List;

public class CompoundOrder extends OrderComponent
{
    private List<OrderComponent> subComponents = new ArrayList<OrderComponent>();

    public CompoundOrder(long order_id, double shippingFees, double totalCost, String shippingAddress, String customerUserName, int quantity)
    {
        super(order_id, shippingFees, totalCost, shippingAddress, customerUserName, quantity);

    }

    public List<OrderComponent> getSubComponents()
    {
        return subComponents;
    }

    public void addComponent(List<OrderComponent> orderComponent)
    {
        this.subComponents = orderComponent;
    }




}

// rep sup