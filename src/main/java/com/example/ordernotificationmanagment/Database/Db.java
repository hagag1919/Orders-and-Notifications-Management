package com.example.ordernotificationmanagment.Database;


import com.example.ordernotificationmanagment.Models.NotificationTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Db {

    private static final Map<Long, NotificationTemplate> templates = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private static final Queue<String> queue = new LinkedList<>();

    public static Queue<String> getQueue() {
        return queue;
    }

    public static void enqueueINQueueList(String notification) {
        queue.add(notification);
    }

    public static String dequeueFromQueueList() {
        return queue.poll();
    }

    public NotificationTemplate findByContent(String content) {
        for (NotificationTemplate template : templates.values()) {
            if (template.getContent().equals(content)) {
                return template;
            }
        }
        return null; // Return null if not found
    }

    private static Db instance;


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




