package com.pragra.shippingapplication.services;

import com.pragra.shippingapplication.dto.ShipmentDTO;
import com.pragra.shippingapplication.dto.ShipmentRequestDTO;
import com.pragra.shippingapplication.kafka.ShipmentProducer;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentProducer shipmentProducer; // Kafka Producer to send shipment events

    //Creates a new shipment  + publishes event to Kafka. + triggers

    public ShipmentDTO createShipment(ShipmentRequestDTO request) {
        Shipment shipment = new Shipment();
        shipment.setOrderId(request.getOrderId());

        // We generate a unique random identifier (UUID), convert it to a string, make it the shipment's tracking no:
        shipment.setTrackingNumber(UUID.randomUUID().toString());

        shipment.setStatus("Shipped"); // must use enums to do this instead
        shipment.setShippedDate(new Date());

        // 5 days delivery estimate. Can make this customizable in a future release.
        shipment.setEstimatedDelivery(new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000));

        // shipment added to repo
        shipmentRepository.save(shipment);

        // Publish shipment event to Kafka
        shipmentProducer.sendShipmentCreatedEvent(shipment);// this method comes from  Kafka Producer Service

        // Publish shipment event to Kafka (instead of calling EmailService directly)
        shipmentProducer.sendShipmentCreatedEvent(shipment);

        return mapToDTO(shipment);
    }

    public ShipmentDTO getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        return mapToDTO(shipment);
    }

    public Optional<ShipmentDTO> updateShipment(Long id, ShipmentRequestDTO request) {
        Optional<Shipment> shipmentOpt = shipmentRepository.findById(id);
        if (shipmentOpt.isPresent()) {
            Shipment shipment = shipmentOpt.get();
            shipment.setOrderId(request.getOrderId());
            shipmentRepository.save(shipment);
            return Optional.of(mapToDTO(shipment));
        }
        return Optional.empty();
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    private ShipmentDTO mapToDTO(Shipment shipment) {
        return new ShipmentDTO(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getTrackingNumber(),
                shipment.getStatus(),
                shipment.getShippedDate(),
                shipment.getEstimatedDelivery(),
                shipment.getUserEmail()
        );
    }
}

