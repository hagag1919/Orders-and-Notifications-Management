package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public  class OrderServices implements  IOrderServices
{
    private final Db db = Db.getInstance();
    private  final  NotificationService notificationService = new NotificationService();

    public boolean pay(SimpleOrder placeOrder){
        List<ProductPlaced> placedProduct = Db.getRightProducts(placeOrder.getProductsSerial());

        if (placedProduct.isEmpty())
        {
            return false;
        }
        double totalCost = 0;
        placeOrder.setShippingFees(0.10);
        List<String> validSerial = new ArrayList<>();
        for (ProductPlaced choiceProduct : placedProduct )
        {
            validSerial.add(choiceProduct.getProductSerial());

        }
        List<Double> price = Db.getPrice(validSerial);
        List<Product> productList = Db.getProductsBySerial(validSerial);
        for(double productCost : price)
        {
            totalCost += productCost + placeOrder.getShippingFees();
        }
        placeOrder.setTotalCost(totalCost);
        if(Db.checkAvailableQuantity(productList) && db.deductionFromBalance(placeOrder.getCustomerUserName(), placeOrder.getTotalCost(), true))
        {
            Db.getPlacedOrders().add(placeOrder);
            for(ProductPlaced product : placedProduct)
            {
                Db.setQuantity(product.getQuantity(), product.getProductSerial());
            }
            return true;
        }
        return false;
    }
    @Override
    public Boolean placeSimpleOrder(SimpleOrder placeOrder)
    {
        Boolean isOk = pay(placeOrder);

        if(isOk)
        {
            List<ProductPlaced> placedProduct = placeOrder.getProductsSerial();

            for (ProductPlaced productPlaced : placedProduct)
            {
                orderTypeTemplate orderTypeTemplate = new placementType(Db.getProductNameBySerial(productPlaced.getProductSerial()),placeOrder.getCustomerUserName());
                NotificationTemplate notificationTemplate  = new NotificationTemplate("placement","Email","Placed Orders","","Arabic, English, French",orderTypeTemplate);
                notificationService.putNotification(notificationTemplate);
            }

        }
        return isOk;
    }

    @Override
    public Boolean placeCompoundOrder(CompoundOrder placeOrder) {


        List<SimpleOrder> friendsOrder = placeOrder.getSubComponents();
        SimpleOrder orderadded = new SimpleOrder(placeOrder.getOrder_id(), placeOrder.getShippingAddress(), placeOrder.getCustomerUserName());
        orderadded.setProductsSerial(placeOrder.getProductsSerial());
        friendsOrder.add(orderadded);

//        List<ProductPlaced> friendsProductSerials = new ArrayList<ProductPlaced>();
//        for (SimpleOrder order : friendsOrder)
//        {
//            friendsProductSerials.addAll(order.getProductsSerial());
//        }
//        List<ProductPlaced> placedProduct = Db.getRightProducts(friendsProductSerials);
//
//        List<String> validSerial = new ArrayList<>();
//        for (ProductPlaced choiceProduct : placedProduct )
//        {
//            validSerial.add(choiceProduct.getProductSerial());
//
//        }
//
//        List<Product> productList = Db.getProductsBySerial(validSerial);

        Set<String> locations = new HashSet<>();
        for (OrderComponent order : friendsOrder) {
            locations.add(order.getShippingAddress().toLowerCase());
        }
//         check if all friends are in the same city
        if (locations.size() > 1) {
            return false;
        }

        boolean isPlaced = true;

        for (SimpleOrder simpleOrder : friendsOrder)
        {
           isPlaced = pay(simpleOrder);
           if (!isPlaced)
           {
               return  false;
           }
        }


        if(isPlaced)
        {
            for(SimpleOrder simpleOrder: friendsOrder)
            {
                for (ProductPlaced product : simpleOrder.getProductsSerial())
                {
                    orderTypeTemplate orderTypeTemplate = new placementType(Db.getProductNameBySerial(product.getProductSerial()),simpleOrder.getCustomerUserName());
                    NotificationTemplate notificationTemplate  = new NotificationTemplate("placement","SMS","Placed Orders","","Arabic, English, French",orderTypeTemplate);
                    notificationService.putNotification(notificationTemplate);
                }
            }

        }



        return  isPlaced;
    }



    @Override
    public List<OrderComponent> getAllPlacedOrder() {
        return Db.getPlacedOrders();
    }


    @Override
    public String cancelOrder(Long orderId) {
        OrderComponent placedOrder = Db.getOrderByID(orderId);

            if(placedOrder != null)
            {
                LocalDateTime orderTime = placedOrder.getPlaceTime();

                // Calculate the duration since order placement
                LocalDateTime currentTime = LocalDateTime.now();
                long durationInMinutes = Duration.between(orderTime, currentTime).toMinutes();

                // Check if within the cancellation duration (e.g., 3 minutes)
                if (durationInMinutes <= 3) {
                    // Cancel the entire order

                    Db.cancelOrder(orderId);
                    return "Successfully canceled the order";
                } else {

                    return "Failed canceling shipping within the time limit ( 3 minutes )";
                }
            }

        return "Order not found or cancellation not allowed";
    }
}
