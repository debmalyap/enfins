package com.qbent.enfinsapp.model;

public class CollectionPoint {
    private String name;
    private String address;
    private String mobileNo;
    private String collectionDay;

    public CollectionPoint(String name, String address, String mobileNo, String collectionDay) {
        this.name = name;
        this.address = address;
        this.mobileNo = mobileNo;
        this.collectionDay = collectionDay;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getCollectionDay() {
        return collectionDay;
    }
}
