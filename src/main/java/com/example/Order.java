package com.example;

public class Order {
    private String customerName;
    private String phoneNumber;
    private String cakeType;
    private String cakeSize;
    private boolean freeDelivery;
    
    public Order(String customerName, String phoneNumber, String cakeType, String cakeSize, boolean freeDelivery) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.cakeType = cakeType;
        this.cakeSize = cakeSize;
        this.freeDelivery = freeDelivery;

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (cakeType == null || cakeType.isBlank()) {
            throw new IllegalArgumentException("Cake type cannot be null or empty.");
        }
        if (cakeSize == null || cakeSize.isBlank()) {
            throw new IllegalArgumentException("Cake size cannot be null or empty.");
        }
    }

    public String getCustomerName() {
        return customerName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCakeType() {
        return cakeType;
    }
    public String getCakeSize() {
        return cakeSize;
    }
    public boolean isFreeDelivery() {
        return freeDelivery;
    }
    public double calculatePrice() {

        double basePrice = 0.0;
        switch (cakeSize) {
            case "Small":
                basePrice = 20.0;
                break;
            case "Medium":
                basePrice = 30.0;
                break;
            case "Large":
                basePrice = 40.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid cake size: " + cakeSize);
        }
        switch (cakeType) {
            case "Coffee":
                basePrice += 5.0;
                break;
            case "Opera":
                basePrice += 5.0;
                break;
            case "Tiramisu":
                basePrice += 5.0;
                break;
            case "Chocolate":
                break;
            case "Apple":
                break;
            case "Cheesecake":
                break;
            case "Carrot":
                break;
            default:
                throw new IllegalArgumentException("Invalid cake type: " + cakeType);
        }

        return basePrice;
    }
}