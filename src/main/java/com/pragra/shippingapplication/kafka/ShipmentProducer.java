package com.pragra.shippingapplication.kafka;

import com.pragra.shippingapplication.dto.ShipmentDTO;
import com.pragra.shippingapplication.model.Shipment;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//This class will send a message to kafka when a shipment is created.

@AllArgsConstructor
@Service
public class ShipmentProducer {

    private final KafkaTemplate<String, ShipmentDTO> kafkaTemplate;

    //Sends shipment creation event to Kafka topic.
    public void sendShipmentCreatedEvent(Shipment shipment) {
        kafkaTemplate.send("shipment-created", shipment);
    }

    public void sendShipmentCreatedEvent(ShipmentDTO shipmentDTO) {
        kafkaTemplate.send("shipment-created", shipmentDTO);
    }
}