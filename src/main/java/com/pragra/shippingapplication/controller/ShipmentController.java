package com.pragra.shippingapplication.controller;


import com.pragra.shippingapplication.dto.ShipmentDTO;
import com.pragra.shippingapplication.dto.ShipmentRequestDTO;
import com.pragra.shippingapplication.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    //create a new Shipment
    @PostMapping
    public ResponseEntity<ShipmentDTO> createShipment(@RequestBody ShipmentRequestDTO request) {
        ShipmentDTO shipmentDTO = shipmentService.createShipment(request);
        return new ResponseEntity<>(shipmentDTO, HttpStatus.CREATED);
    }        //ResponseEntity = what was processed (data)+ if the request was successful + Any extra header details (security, caching info etc)

    // get Shipment based on ID.
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDTO> getShipmentById(@PathVariable Long id) {
        Optional<ShipmentDTO> shipment = Optional.ofNullable(shipmentService.getShipmentById(id));// Optional wrapping
        return shipment.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 if it's a null
    }

    //update shipment
    public ResponseEntity<ShipmentDTO> updateShipment(
            @PathVariable Long id,
            @RequestBody ShipmentRequestDTO request) {

        Optional<ShipmentDTO> updatedShipment = shipmentService.updateShipment(id, request);
        return updatedShipment.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // delete shipment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

