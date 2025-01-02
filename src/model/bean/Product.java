package model.bean;

public class Product {
    private String orderID;          // OrderID (CHAR(5))
    private String payer;            // Payer (VARCHAR(15))
    private String pickupCity;       // PickupCity (VARCHAR(50))
    private String pickupDistrict;   // PickupDistrict (VARCHAR(30))
    private String pickupWard;       // PickupWard (VARCHAR(30))
    private String pickupAddress;    // PickupAddress (VARCHAR(45))
    private String recipientName;    // RecipientName (VARCHAR(30))
    private String phoneRecipient;   // PhoneRecipient (VARCHAR(15))
    private String deliveryCity;     // DeliveryCity (VARCHAR(50))
    private String deliveryDistrict; // DeliveryDistrict (VARCHAR(45))
    private String deliveryWard;     // DeliveryWard (VARCHAR(45))
    private String deliveryAddress;  // DeliveryAddress (VARCHAR(45))
    private String serviceID;        // ServiceID (CHAR(5))

    // Constructor không tham số
    public Product() {
        super();
    }

    // Constructor đầy đủ tham số
    public Product(String orderID, String payer, String pickupCity, String pickupDistrict, String pickupWard,
                   String pickupAddress, String recipientName, String phoneRecipient, String deliveryCity,
                   String deliveryDistrict, String deliveryWard, String deliveryAddress, String serviceID) {
        this.orderID = orderID;
        this.payer = payer;
        this.pickupCity = pickupCity;
        this.pickupDistrict = pickupDistrict;
        this.pickupWard = pickupWard;
        this.pickupAddress = pickupAddress;
        this.recipientName = recipientName;
        this.phoneRecipient = phoneRecipient;
        this.deliveryCity = deliveryCity;
        this.deliveryDistrict = deliveryDistrict;
        this.deliveryWard = deliveryWard;
        this.deliveryAddress = deliveryAddress;
        this.serviceID = serviceID;
    }

    // Getter và Setter cho các thuộc tính
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPickupCity() {
        return pickupCity;
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity = pickupCity;
    }

    public String getPickupDistrict() {
        return pickupDistrict;
    }

    public void setPickupDistrict(String pickupDistrict) {
        this.pickupDistrict = pickupDistrict;
    }

    public String getPickupWard() {
        return pickupWard;
    }

    public void setPickupWard(String pickupWard) {
        this.pickupWard = pickupWard;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneRecipient() {
        return phoneRecipient;
    }

    public void setPhoneRecipient(String phoneRecipient) {
        this.phoneRecipient = phoneRecipient;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }

    public String getDeliveryWard() {
        return deliveryWard;
    }

    public void setDeliveryWard(String deliveryWard) {
        this.deliveryWard = deliveryWard;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "orderID='" + orderID + '\'' +
                ", payer='" + payer + '\'' +
                ", pickupCity='" + pickupCity + '\'' +
                ", pickupDistrict='" + pickupDistrict + '\'' +
                ", pickupWard='" + pickupWard + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", phoneRecipient='" + phoneRecipient + '\'' +
                ", deliveryCity='" + deliveryCity + '\'' +
                ", deliveryDistrict='" + deliveryDistrict + '\'' +
                ", deliveryWard='" + deliveryWard + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", serviceID='" + serviceID + '\'' +
                '}';
    }
}

