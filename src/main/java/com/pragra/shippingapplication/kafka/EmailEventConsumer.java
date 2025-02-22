package com.pragra.shippingapplication.kafka;

import com.pragra.shippingapplication.dto.ShipmentDTO;
import com.pragra.shippingapplication.services.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// This class listens for shipment events and triggers email notifications.

@Service
public class EmailEventConsumer {

    private final EmailService emailService;

    // all args constructor
    public EmailEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    // Listens for shipment-created events and sends an email.
    @KafkaListener(topics = "shipment-created", groupId = "email-service-group")
    public void consumeShipmentEvent(ShipmentDTO shipmentDTO) {
        System.out.println("📨 Received Shipment Event: " + shipmentDTO);

        // Send email notification using EmailService
        emailService.sendShipmentEmail(
                shipmentDTO.getUserEmail(),
                shipmentDTO.getTrackingNumber(),
                shipmentDTO.getEstimatedDelivery().toString()
        );
        //EmailService is now fully decoupled from ShipmentService.
    }
}
