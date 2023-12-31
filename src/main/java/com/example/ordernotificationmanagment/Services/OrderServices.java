package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public  class OrderServices implements  IOrderServices
{
    private final Db db = Db.getInstance();

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
        return pay(placeOrder);
    }

    @Override
    public Boolean placeCompoundOrder(CompoundOrder placeOrder) {


        List<SimpleOrder> friendsOrder = placeOrder.getSubComponents();
        SimpleOrder orderadded = new SimpleOrder(placeOrder.getOrder_id(), placeOrder.getShippingAddress(), placeOrder.getCustomerUserName());
        friendsOrder.add(orderadded);

        Set<String> locations = new HashSet<>();
        for (OrderComponent order : friendsOrder) {
            locations.add(order.getShippingAddress().toLowerCase());
        }
        // check if all friends are in the same city
        if (locations.size() > 1) {
            return false;
        }

        boolean isPlaced = true;

        for (SimpleOrder simpleOrder : friendsOrder)
        {
           isPlaced &= pay(simpleOrder);
        }

        return  isPlaced;
    }

    @Override
    public List<OrderComponent> getAllPlacedOrder() {
        return db.getPlacedOrders();
    }
}
