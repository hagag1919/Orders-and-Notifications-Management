package com.example.ordernotificationmanagment.Models;

public class StatisticsReport {

    private String mostMethod;
    private String mostTemplate;

    private  String mostRecipient;


    public StatisticsReport(String mostMethod, String mostTemplate,String mostRecipient) {
        this.mostMethod = mostMethod;
        this.mostTemplate = mostTemplate;
        this.mostRecipient = mostRecipient;
    }

    public void setMostMethod(String mostMethod) {
        this.mostMethod = mostMethod;
    }

    public void setMostTemplate(String mostTemplate) {
        this.mostTemplate = mostTemplate;
    }

    public void setMostRecipient(String mostRecipient) {
        this.mostRecipient = mostRecipient;
    }


    public String getMostRecipient() {
        return mostRecipient;
    }

    public String getMostMethod() {
        return mostMethod;
    }

    public String getMostTemplate() {
        return mostTemplate;
    }
}
