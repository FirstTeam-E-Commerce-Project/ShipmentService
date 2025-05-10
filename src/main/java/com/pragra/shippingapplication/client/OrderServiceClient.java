package com.pragra.shippingapplication.client;

import com.pragra.shippingapplication.dto.ShipmentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;


// This interface defines communication with the Order Service.


@FeignClient(name = "order-service", url = "http://order-service/api")
public interface OrderServiceClient {

    @PostMapping("/orders/{id}/shipment")
    ShipmentResponseDTO createShipment(@PathVariable("id") Long id, @RequestBody ShipmentResponseDTO shipmentDTO);

    @GetMapping("/orders/{id}")
    ShipmentResponseDTO getShipmentById(@PathVariable("id") Long id);
}
