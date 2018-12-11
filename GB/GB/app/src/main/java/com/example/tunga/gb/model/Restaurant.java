package com.example.tunga.gb.model;

public class Restaurant {
    private String telNo;
    private int capacityOfPeople;
    private int capaOfTable;
    private int rate;
    private int ownerID;
    private String featuresInfo;
    private String location;


    public Restaurant() {
    }


    public int getCapacityOfPeople() {
        return capacityOfPeople;
    }

    public void setCapacityOfPeople(int capacityOfPeople) {
        this.capacityOfPeople = capacityOfPeople;
    }

    public int getCapaOfTable() {
        return capaOfTable;
    }

    public void setCapaOfTable(int capaOfTable) {
        this.capaOfTable = capaOfTable;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getFeaturesInfo() {
        return featuresInfo;
    }

    public void setFeaturesInfo(String featuresInfo) {
        this.featuresInfo = featuresInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelNo() {

        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
