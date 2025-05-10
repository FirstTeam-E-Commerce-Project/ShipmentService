package com.pragra.shippingapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ShipmentResponseDTO {//Used by Services That READ Shipments
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String status;
    private Date shippedDate;
    private Date estimatedDelivery;
    private String userEmail;

//all args constructor
    public ShipmentResponseDTO(Long id, Long orderId, String trackingNumber, String status, Date shippedDate, Date estimatedDelivery, String userEmail) {
        this.id = id;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.shippedDate = shippedDate;
        this.estimatedDelivery = estimatedDelivery;
        this.userEmail = userEmail;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getShippedDate() { return shippedDate; }
    public void setShippedDate(Date shippedDate) { this.shippedDate = shippedDate; }

    public Date getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(Date estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }


}
