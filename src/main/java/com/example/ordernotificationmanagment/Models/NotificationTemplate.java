package com.example.ordernotificationmanagment.Models;

public class NotificationTemplate {




    private  String templateType;
    private String subject;
    private  String content = "Thank you for using our website ";

    private  String sendMethod = "SMS";
    private String availableLanguages = "Ar,En,Ru";
    private  orderPlacementTemplate placeholders;
    private Long id;
    


    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public NotificationTemplate(String templateType,String sendMethod, String subject, String content , String availableLanguages, orderPlacementTemplate placeholders)
    {
        this.templateType = templateType;
        this.subject = subject;
        this.placeholders = placeholders;
       if(content !=null)
       {
           this.content = content;
       }
        if(sendMethod !=null)
        {
            this.sendMethod = sendMethod;
        }
        if(availableLanguages != null)
        {
            this.availableLanguages = availableLanguages;
        }


    }

    public String getContent() {
        return content;
    }

    public orderPlacementTemplate getPlaceholders() {
        return placeholders;
    }

    public String getTemplateType() {
        return templateType;
    }

    public String getSubject() {
        return subject;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
    }

    public void setPlaceholders(orderPlacementTemplate placeholders) {
        this.placeholders = placeholders;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }
}


