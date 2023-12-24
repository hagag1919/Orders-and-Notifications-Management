package com.example.ordernotificationmanagment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ordernotificationmanagment.Models.NotificationTemplate;
import com.example.ordernotificationmanagment.Models.orderPlacementTemplate;
import com.example.ordernotificationmanagment.Services.NotificationService;

import java.util.List;
import java.util.Locale;

@RestController
public class AppController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping("/")
    public String index() {
        return "Welcome To our system";
    }

    /*     <<<<<<< Notifications Part >>>>>>       */
    @PostMapping("notifications/post")
    public ResponseEntity<String> createNotification(
            @RequestParam String templateType,
            @RequestParam String subject,
            @RequestParam String productName,
            @RequestParam String recipientName,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String sendMethod,
            @RequestParam(required = false) String availableLanguages


    ) {
        orderPlacementTemplate orderPlacement = new orderPlacementTemplate(productName,recipientName);

        NotificationTemplate newTemplate = new NotificationTemplate(templateType.toLowerCase(Locale.ROOT),sendMethod,subject,content,availableLanguages,orderPlacement);

        Boolean isCreated = notificationService.putNotification(newTemplate);

        if (isCreated ) {
            return new ResponseEntity<>("Notification created with ID: " + newTemplate.getId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create notification", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/notifications/getAll")
    public ResponseEntity<List<NotificationTemplate>> getAllTemplates() {
        try {
            List<NotificationTemplate> templates = notificationService.getAllNotifications();
            return new ResponseEntity<>(templates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notifications/get")
    public ResponseEntity<NotificationTemplate> get(@RequestParam Long id) {
        try {
            NotificationTemplate template = notificationService.getNotification(id);
            if (template != null) {
                return new ResponseEntity<>(template, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notifications/queue")
    public List<NotificationTemplate> getNotificationQueue() {
        return notificationService.getNotificationQueue();
    }

    @GetMapping("/notifications/push")
    public ResponseEntity<String> dequeueAndSendNotification() {
        NotificationTemplate dequeuedNotification = notificationService.dequeueNotification();

        if (dequeuedNotification != null) {
            return new ResponseEntity<>("Notification pushed and sent successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No notifications in the queue", HttpStatus.NOT_FOUND);
        }
    }




}
