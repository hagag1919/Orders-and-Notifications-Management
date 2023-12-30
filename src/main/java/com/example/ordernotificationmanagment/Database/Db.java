package com.example.ordernotificationmanagment.Database;


import com.example.ordernotificationmanagment.Models.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Db {

    private static final Map<Long, NotificationTemplate> templates = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private static final Queue<NotificationTemplate> queue = new LinkedList<>();

    public static Queue<NotificationTemplate> getQueue() {
        return queue;
    }

    public static void enqueueINQueueList(NotificationTemplate notification) {
        queue.add(notification);
    }

    public static NotificationTemplate dequeueFromQueueList() {
        return queue.poll();
    }


    private static Db instance;

    private static List<Category> categories = new ArrayList<Category>();//All Categories IN System
    private static List<Product> products = new ArrayList<Product>(); //All Products IN System
    private static List<Customer> customers = new ArrayList<Customer>();//All Customers IN System
    private static List<OrderComponent> placedOrders = new ArrayList<OrderComponent>();

    public static List<OrderComponent> getPlacedOrders() {
        return placedOrders;
    }

    public static List<Category> getCategories()
    {
        return categories;
    }

    public static List<Product> getProducts()
    {
        return products;
    }

    public static List<Customer> getCustomers()
    {
        return customers;
    }

    public static Boolean checkAvailableQuantity(List<Product> products)
    {
        for(Product product : products)
        {
            for(Product product1 : Db.products)
            {
                if(product.getSerialNumber().equals(product1.getSerialNumber()))
                {
                    if(product.getReminder() > product1.getReminder())
                    {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
    public static Boolean setQuantity(int productNum, String productID)
    {
        for(Product product : products)
        {
           if(productID.equals(product.getSerialNumber()) && productNum <= product.getReminder()) {

               product.setReminder(product.getReminder() - productNum);
               return true;
           }
        }
        return false;
    }




    public List<NotificationTemplate> getAllTemplates() {
        return new ArrayList<>(templates.values());
    }
    public static Db getInstance() {
        if (instance == null) {
            synchronized (Db.class) {
                if (instance == null) {
                    instance = new Db();
                    instance.addSampleData();
                }
            }
        }
        return instance;
    }

    public Map<Long, NotificationTemplate> getTemplates() {
        return templates;
    }

    public void addTemplate( NotificationTemplate template) {

         template.setId(idGenerator.getAndIncrement());
        templates.put(template.getId(), template);
    }

    public static List<Product> getRightProducts(List<Product> oldProducts)
    {
        List<Product> newProducts = new ArrayList<>();
        for(Product product : oldProducts)
        {
            for(Product product1 : Db.products)
            {
                if(product.getSerialNumber().equals(product1.getSerialNumber()))
                {
                    newProducts.add(product);
                    break;
                }
            }
        }
        System.out.println("newProducts = ");
        for(Product product : newProducts)
        {
            System.out.print(product);
        }
        return newProducts;
    }
    public Boolean deductionFromBalance(String userName, double deductFees, boolean makeTransaction)
    {
        for(Customer customer : customers)
        {
            if(userName.equals(customer.getUserName()) && deductFees <= customer.getBalance())
            {
                if(makeTransaction) customer.setBalance(customer.getBalance() - deductFees);//update balance
                return  true;
            }
        }
        return  false;
    }

    public NotificationTemplate getTemplateById(Long id) {
        return templates.get(id);
    }

    private void addSampleData() {
        NotificationTemplate template1 = new NotificationTemplate(null, null, null, null, null, null);
        template1.setTemplateType("OrderPlacement");


        template1.setSubject("Order Placement Confirmation");
        template1.setContent("Dear {customer}, your order of {item} has been confirmed.");

        NotificationTemplate template2 = new NotificationTemplate(null, null, null, null, null, null);

        template2.setTemplateType("OrderShipment");
        template2.setSubject("Order Shipment Notification");
        template2.setContent("Hi {customer}, your order containing {item} has been shipped.");

        // Add the sample templates to the Db
        addTemplate(template1);
        addTemplate(template2);
    }
}




