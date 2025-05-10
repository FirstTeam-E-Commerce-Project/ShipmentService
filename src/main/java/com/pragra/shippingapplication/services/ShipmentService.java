package com.pragra.shippingapplication.services;

import com.pragra.shippingapplication.client.OrderServiceClient;
import com.pragra.shippingapplication.dto.ShipmentResponseDTO;
import com.pragra.shippingapplication.dto.CreateShipmentRequestDTO;
import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderServiceClient orderServiceClient; // Feign Client to communicate with Order Service

    public ShipmentService(ShipmentRepository shipmentRepository, OrderServiceClient orderServiceClient) {
        this.shipmentRepository = shipmentRepository;
        this.orderServiceClient = orderServiceClient;
    }

    // CREATES NEW SHIPMENT
    public ShipmentResponseDTO createShipment(CreateShipmentRequestDTO request) {
        Shipment shipment = new Shipment();
        shipment.setOrderId(request.getOrderId());
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setStatus("Shipped");
        shipment.setShippedDate(new Date());
        shipment.setEstimatedDelivery(new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000));

        shipmentRepository.save(shipment);

        // Send shipment details to Order Service using Feign Client
        ShipmentResponseDTO shipmentDTO = mapToDTO(shipment);
        orderServiceClient.createShipment(shipment.getOrderId(), shipmentDTO);

        return shipmentDTO;
    }


    // READS SHIPMENT
    public ShipmentResponseDTO getShipmentById(Long id) {
        return orderServiceClient.getShipmentById(id);
    }


    //UPDATES SHIPMENT
    public Optional<ShipmentResponseDTO> updateShipment(Long id, CreateShipmentRequestDTO request) {
        Optional<Shipment> shipmentOpt = shipmentRepository.findById(id);
        if (shipmentOpt.isPresent()) {
            Shipment shipment = shipmentOpt.get();
            shipment.setOrderId(request.getOrderId());
            shipmentRepository.save(shipment);
            return Optional.of(mapToDTO(shipment));
        }
        return Optional.empty();
    }

    // DELETES SHIPMENT
    public boolean deleteShipment(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if (shipment.isPresent()) {
            shipmentRepository.delete(shipment.get());
            return true;
        }
        return false;
    }

    // ShipmentResponseDTO mapped to Shipment Entity
    private ShipmentResponseDTO mapToDTO(Shipment shipment) {
        return new ShipmentResponseDTO(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getTrackingNumber(),
                shipment.getStatus(),
                shipment.getShippedDate(),
                shipment.getEstimatedDelivery(),
                shipment.getUserEmail()
        ); /// TODO : TRY USING MODEL MAPPER
    }
}
