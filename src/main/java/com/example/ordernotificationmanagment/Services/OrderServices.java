package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.CompoundOrder;
import com.example.ordernotificationmanagment.Models.OrderComponent;
import com.example.ordernotificationmanagment.Models.Product;
import com.example.ordernotificationmanagment.Models.SimpleOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public  class OrderServices implements  IOrderServices
{
    private final Db db = Db.getInstance();

    public boolean pay(OrderComponent placeOrder){
        List<Product> placedProduct = Db.getRightProducts(placeOrder.getProducts());
        if (placedProduct.isEmpty())
        {
            return false;
        }
        double totalCost = 0;
        placeOrder.setShippingFees(0.10);
        for(Product product : placedProduct)
        {
            totalCost += product.getPrice() + placeOrder.getShippingFees();
        }
        placeOrder.setTotalCost(totalCost);
        if(Db.checkAvailableQuantity(placedProduct) && db.deductionFromBalance(placeOrder.getCustomerUserName(), placeOrder.getTotalCost(), true))
        {
            Db.getPlacedOrders().add(placeOrder);
            for(Product product : placedProduct)
            {
                Db.setQuantity(product.getReminder(), product.getSerialNumber());
            }
            return true;
        }
        return false;
    }
    @Override
    public Boolean placeSimpleOrder(SimpleOrder placeOrder)
    {
        return pay(placeOrder);
    }

    @Override
    public Boolean placeCompoundOrder(CompoundOrder placeOrder) {

        List<OrderComponent> friendsOrder = placeOrder.getSubComponents();
        OrderComponent orderadded = new SimpleOrder(placeOrder.getOrder_id(), placeOrder.getShippingAddress(), placeOrder.getCustomerUserName());
        orderadded.setProducts(Db.getRightProducts(placeOrder.getProducts()));
        friendsOrder.add(orderadded);
//        List<Product> placedProduct = new ArrayList<>();
//        
//        for (OrderComponent order : friendsOrder){
//            for(Product product : order.getProducts())
//            {
//                placedProduct.add(product);
//            }
//        }


        Set<String> locations = new HashSet<>();
        for (OrderComponent order : friendsOrder) {
            locations.add(order.getShippingAddress().toLowerCase());
        }
        // check if all friends are in the same city
        if (locations.size() > 1) {
            return false;
        }
        boolean isPlaced = true;
        double friendCost = 0.0;
        for (OrderComponent order : friendsOrder) {
            for (Product product : order.getProducts()) {
                placeOrder.setShippingFees(0.11);
                friendCost += product.getPrice() + placeOrder.getShippingFees();
            }
            order.setTotalCost(friendCost);
            isPlaced &= Db.checkAvailableQuantity(order.getProducts());
            isPlaced &= db.deductionFromBalance(order.getCustomerUserName(), order.getTotalCost() - order.getShippingFees(), false);
        }
        if (isPlaced) for (OrderComponent order : friendsOrder) {
            for (Product product : order.getProducts()) {
                placeOrder.setShippingFees(0.11);
                friendCost += product.getPrice() + placeOrder.getShippingFees();
            }
            order.setTotalCost(friendCost);
            db.deductionFromBalance(order.getCustomerUserName(), order.getTotalCost() - order.getShippingFees(), true);
        }

        // for me
        List<Product> placedProduct = Db.getRightProducts(placeOrder.getProducts());
        placeOrder.setShippingFees(0.10);
        double totalCost = 0.0;
        for (Product product : placedProduct) {
            totalCost += product.getPrice() + placeOrder.getShippingFees();
        }
        placeOrder.setTotalCost(totalCost);
        if (Db.checkAvailableQuantity(placedProduct) && db.deductionFromBalance(placeOrder.getCustomerUserName(), placeOrder.getTotalCost(), true)) {
            Db.getPlacedOrders().add(placeOrder);
            for (Product product : placedProduct) {
                Db.setQuantity(product.getReminder(), product.getSerialNumber());
            }
            return true;
        }


        return  isPlaced;
    }

    @Override
    public List<OrderComponent> getAllPlacedOrder() {
        return db.getPlacedOrders();
    }
}
