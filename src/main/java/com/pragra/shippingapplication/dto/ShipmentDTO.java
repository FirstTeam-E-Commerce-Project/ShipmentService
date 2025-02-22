package com.pragra.shippingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String status;
    private Date shippedDate;
    private Date estimatedDelivery;
    private String userEmail;
}
