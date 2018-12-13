package com.example.tunga.gb.model;

public class Restaurant {
    public String telNo;
    public String restaurantName;
    public int capacityOfPeople;
    public int capaOfTable;
    public int rate;
    public int ownerID;
    public String featuresInfo;
    public String location;
    public int restaurantID;

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Restaurant() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
