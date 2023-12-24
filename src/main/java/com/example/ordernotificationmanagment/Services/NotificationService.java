package com.example.ordernotificationmanagment.Services;


import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.NotificationTemplate;
import com.example.ordernotificationmanagment.Models.orderPlacementTemplate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

     private Db db = Db.getInstance();
     private EmailService emailService ;
     private  SMSService smsService;

    private String pushPlacementNotification(orderPlacementTemplate order)
    {
        return ("Dear" +order.getRecipientName()+ ", your booking of the "+order.getProductName()+" is confirmed. thanks for using our store :)");
    }


    private String sendShipmentNotification(orderPlacementTemplate order) {
        return ("Dear " + order.getRecipientName() + ", your shipment containing " + order.getProductName() + " has been confirmed. Thank you for choosing our store!");
    }


    public List<NotificationTemplate> getAllNotifications() {
        // Assuming Db class has a method to retrieve all templates
        return db.getAllTemplates();
    }



    public Boolean putNotification(NotificationTemplate ordernotificationManagment)
    {
        NotificationTemplate orderNotification = ordernotificationManagment;
      if(ordernotificationManagment.getTemplateType().equals("placement"))
      {
         orderNotification.setContent(pushPlacementNotification(orderNotification.getPlaceholders()));
        
      }
      else if (ordernotificationManagment.getTemplateType().equals("shipment"))
      {
         orderNotification.setContent(sendShipmentNotification(orderNotification.getPlaceholders()));
      }
      else {
          return false;
      }

      db.addTemplate(orderNotification);
      db.enqueueINQueueList(orderNotification);

      return true;
    }


    public NotificationTemplate dequeueNotification() {
        NotificationTemplate notification = db.dequeueFromQueueList();
        if (notification != null) {
            if (notification.getSendMethod().equalsIgnoreCase("SMS")) {

                smsService.sendSMS(notification.getPlaceholders().getRecipientName(), notification.getContent());
            } else if (notification.getSendMethod().equalsIgnoreCase("Email")) {

                emailService.sendEmail(notification.getPlaceholders().getRecipientName(), notification.getSubject(), notification.getContent());
            }
        }
        return notification;
    }

    public NotificationTemplate getNotification(Long id)
    {
        return db.getTemplateById(id);
        
    }


    public List<NotificationTemplate> getNotificationQueue() {
        return new ArrayList<>(db.getQueue());
    }


}

