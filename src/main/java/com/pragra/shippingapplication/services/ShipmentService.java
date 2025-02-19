package com.pragra.shippingapplication.services;

import org.springframework.stereotype.Service;
import com.pragra.shippingapplication.services.ShipmentService.client.UserClient;
import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    // TODO : Yet to create UserClient OrderService and EmailService.

    @Autowired
    // TODO: UserClient class would ready the userDTO from User Application for Shipment's use
    private UserClient userClient;

    @Autowired
    // TODO: OrderClient class would ready the orderDTO from Order Application for Shipment's use
    private OrderClient orderClient;

    // TODO: EmailService would take Shipment out to SMTP servers
    @Autowired
    private EmailService emailService;

    public Shipment createShipment(Long orderId, Long userId) {

        // TODO: Must Validate order or retrieve order here from Order microservice before proceeding.

        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);

        // Here we generate a unique random identifier (UUID), convert it to a string,
        // and assigns it as the shipment's tracking number
        shipment.setTrackingNumber(UUID.randomUUID().toString()); //

        shipment.setStatus("Shipped"); // must use enums to do this instead

        shipment.setShippedDate(new Date());

        // 5 days delivery estimate. Can make this customizable in a future release.
        shipment.setEstimatedDelivery(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000));

        // Get user email from User management microservice
        String userEmail = userClient.getUserEmail(userId);
        shipment.setUserEmail(userEmail);

        shipmentRepository.save(shipment);
        emailService.sendShipmentEmail(userEmail, shipment.getTrackingNumber(), shipment.getEstimatedDelivery());

        return shipment;
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public Shipment updateShipment(Long id, Long orderId, Long userId) {
        Shipment shipment = getShipmentById(id);
        shipment.setOrderId(orderId);
        // Additional update logic can be added here if needed.
        shipmentRepository.save(shipment);
        return shipment;
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
}

