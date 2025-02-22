package com.pragra.shippingapplication.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic shipmentCreatedTopic() {
        return TopicBuilder.name("shipment-created")
                .partitions(3) // Splits messages across 3 partitions
                .replicas(1)    // One copy of each partition
                .build();
    }

    @Bean
    public NewTopic orderValidatedTopic() {
        return TopicBuilder.name("order-validated").
                partitions(3).
                replicas(1).
                build();
    }
}
