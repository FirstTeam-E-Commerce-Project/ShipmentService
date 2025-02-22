package com.pragra.shippingapplication.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/*
When OrderService microservice completes  a 'checkout' it sends a message to Kafka saying the order is validatad.
This 'consumer' class listens to order validation events from Kafka.
So instead of calling EmailService or OrderService directly, we listen for a message.
Kafka ensures that the message is delivered even if services are temporarily down.
*/

@Service
public class OrderValidationConsumer {

    @KafkaListener(topics = "order-validated", groupId = "shipping-group") // Listens to "order-validated" topic and processes incoming messages.
    public void processOrderValidation(String message) {
        System.out.println("Received order validation message: " + message);


        // TODO Add Logic to update shipment status if needed
    }
}