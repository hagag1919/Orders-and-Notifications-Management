package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Models.CompoundOrder;
import com.example.ordernotificationmanagment.Models.OrderComponent;
import com.example.ordernotificationmanagment.Models.SimpleOrder;

import java.util.List;

public interface IOrderServices {

    public Boolean placeSimpleOrder(SimpleOrder placeOrder);
    public Boolean placeCompoundOrder(CompoundOrder placeOrder);
    public List<OrderComponent>  getAllPlacedOrder();


}
