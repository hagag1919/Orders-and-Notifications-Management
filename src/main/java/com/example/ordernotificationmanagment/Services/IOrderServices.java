package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Models.OrderComponent;

import java.util.List;

public interface IOrderServices {

    Boolean placeOrder(OrderComponent placeOrder);
    List<OrderComponent>  getAllPlacedOrder();

}
