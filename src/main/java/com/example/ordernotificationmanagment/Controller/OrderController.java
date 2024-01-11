package com.example.ordernotificationmanagment.Controller;

import com.example.ordernotificationmanagment.Models.CompoundOrder;
import com.example.ordernotificationmanagment.Models.OrderComponent;
import com.example.ordernotificationmanagment.Models.SimpleOrder;
import com.example.ordernotificationmanagment.Services.IOrderServices;
import com.example.ordernotificationmanagment.Services.NotificationService;
import com.example.ordernotificationmanagment.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired private final IOrderServices orderServices = new OrderServices();

    @PostMapping(value="/place-simple-order")
    public String placeSimpleOrder(@RequestBody SimpleOrder placeOrder)
    {
        if(this.orderServices.placeSimpleOrder(placeOrder)){
            return "Order Placed Successfully";
        }
        else{
            return "Order Placed Failed";
        }
    }

    @PostMapping(value="/place-compound-order")
    public String placeCompoundOrder(@RequestBody CompoundOrder placeOrder)
    {
        if(this.orderServices.placeCompoundOrder(placeOrder)){

            return "Order Placed Successfully";
        }
        else{
            return "Order Placed Failed";
        }
    }
    @GetMapping(value="/get-all-orders")
    public List<OrderComponent> getAllPlacedOrder()
    {
        return this.orderServices.getAllPlacedOrder();
    }


    @PostMapping(value="/cancel-order")
    public String cancelOrder(@RequestParam Long orderId) {

        return orderServices.cancelOrder(orderId);
    }
}
