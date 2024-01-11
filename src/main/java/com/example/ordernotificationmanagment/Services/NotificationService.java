package com.example.ordernotificationmanagment.Services;


import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.NotificationTemplate;
import com.example.ordernotificationmanagment.Models.StatisticsReport;
import com.example.ordernotificationmanagment.Models.orderTypeTemplate;

import com.example.ordernotificationmanagment.Models.shipmentType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

     private Db db = Db.getInstance();
     private EmailService emailService ;
     private  SMSService smsService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> removalTask; // Added variable to hold the scheduled task

    @PostConstruct
    private void initialize() {
        scheduleNotificationRemoval();
    }

    private void scheduleNotificationRemoval() {
        if (removalTask == null || removalTask.isDone()) { // Check if no task exists or the existing one is done
            removalTask = scheduler.schedule(() -> {
                Db.dequeueFromQueueList();
            }, 1, TimeUnit.MINUTES);// the delay is set to  1 Minutes
        }
    }

    private String pushPlacementNotification(orderTypeTemplate order)
    {
        return ("Dear " +order.getRecipientName()+ ", your booking of the "+order.getProductName()+" is confirmed. thanks for using our store :)");
    }


    private String sendShipmentNotification(orderTypeTemplate order) {
        if (order instanceof shipmentType) {
            shipmentType shipment = (shipmentType) order;
            return ("Dear " + shipment.getRecipientName() + ", your shipment containing " + shipment.getProductName() + " by " + shipment.getShipCompanyName() + " has been confirmed. Thank you for choosing our store!");
        }
        return "";
    }


    public List<NotificationTemplate> getAllNotifications() {
        return new ArrayList<> (Db.getemplateQueue());
    }



    public Boolean putNotification(NotificationTemplate ordernotificationManagment)
    {
        if(ordernotificationManagment.getTemplateType().equals("placement"))
      {
         ordernotificationManagment.setContent(pushPlacementNotification(ordernotificationManagment.getPlaceholders()));
        
      }
      else if (ordernotificationManagment.getTemplateType().equals("shipment"))
      {
         ordernotificationManagment.setContent(sendShipmentNotification(ordernotificationManagment.getPlaceholders()));
      }
      else {
          return false;
      }

      db.addTemplate(ordernotificationManagment);
      scheduleNotificationRemoval();

      return true;
    }



    public NotificationTemplate dequeueNotification() {
        NotificationTemplate notification = Db.dequeueFromQueueList();
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

    public StatisticsReport getTheNotificationsReport() {
        StatisticsReport statisticsReport = new StatisticsReport("", "", "");
        double totalNotifications = db.getTemplates().size();
        double emailMethod = 0.0;
        double smsMethod = 0.0;
        double shipmentMethod = 0.0;
        double placementMethod = 0.0;
        Map<String, Integer> accountFrequencyMap = new HashMap<>();
        for (NotificationTemplate notificationTemplate : db.getTemplates()) {

            updateAccountFrequency(notificationTemplate.getPlaceholders().getRecipientName(), accountFrequencyMap);

            if (notificationTemplate.getSendMethod().equals("email")) {
                emailMethod++;
            } else {
                smsMethod++;
            }
            if (notificationTemplate.getTemplateType().equals("shipment")) {
                shipmentMethod++;
            } else {
                placementMethod++;
            }
        }
        double mostMethodPercentage ;
        double mostTemplatePercentage ;
        if(emailMethod > smsMethod)
        {
            mostMethodPercentage = (emailMethod / totalNotifications) * 100;
            statisticsReport.setMostMethod("The most used method is Email with a percentage of " + mostMethodPercentage + "%");
        }
        else {
            mostMethodPercentage = (smsMethod / totalNotifications) * 100;
            statisticsReport.setMostMethod("The most used method is SMS with a percentage of " + mostMethodPercentage  + "%");
        }
        if(shipmentMethod > placementMethod)
        {
            mostTemplatePercentage = (shipmentMethod / totalNotifications) * 100;

            statisticsReport.setMostTemplate("The most used template is Shipment with a percentage of " + mostTemplatePercentage  + "%");
        }
        else
        {
            mostTemplatePercentage = (placementMethod / totalNotifications) * 100;

            statisticsReport.setMostTemplate("The most used template is Placement with a percentage of " + mostTemplatePercentage  + "%");
        }

        if(mostMethodPercentage ==0.0 && mostTemplatePercentage ==0)
        {
            statisticsReport.setMostMethod("No Notifications");
            statisticsReport.setMostTemplate("No Notifications");
        }
        String mostAccountRecipient = getMostAccountRecipient(accountFrequencyMap);
        statisticsReport.setMostRecipient("The most frequent account recipient is: " + mostAccountRecipient);
        if (statisticsReport.getMostMethod().isEmpty() && statisticsReport.getMostTemplate().isEmpty() && mostAccountRecipient.isEmpty()) {
            statisticsReport.setMostMethod("No Notifications");
            statisticsReport.setMostTemplate("No Notifications");
            statisticsReport.setMostRecipient("No Notifications");
        }



        return statisticsReport;

    }

    private void updateAccountFrequency(String account, Map<String, Integer> accountFrequencyMap) {
        accountFrequencyMap.put(account, accountFrequencyMap.getOrDefault(account, 0) + 1);
    }

    private String getMostAccountRecipient(Map<String, Integer> accountFrequencyMap) {
        String mostAccountRecipient = " ";
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : accountFrequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostAccountRecipient = entry.getKey();
            }
        }

        return mostAccountRecipient;
    }





    public List<NotificationTemplate> getNotificationQueue() {

        return new ArrayList<>(db.getTemplates());
    }


}

