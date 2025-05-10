package com.pragra.shippingapplication.dto;
import lombok.Data;

@Data
public class CreateShipmentRequestDTO {//Used by Services That CREATE Shipments

    private Long orderId;  //The order associated with this shipment
    private Long userId; // The user placing the order

    //Getters and Setters
    public Long getOrderId() {return orderId;}
    public void setOrderId(Long orderId) {this.orderId = orderId;}
    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
}
