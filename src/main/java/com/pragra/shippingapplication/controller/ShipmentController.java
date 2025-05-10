package com.pragra.shippingapplication.controller;


import com.pragra.shippingapplication.dto.ShipmentResponseDTO;
import com.pragra.shippingapplication.dto.CreateShipmentRequestDTO;
import com.pragra.shippingapplication.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    //create a new Shipment
    @PostMapping("/")
    public ResponseEntity<ShipmentResponseDTO> createShipment(@RequestBody CreateShipmentRequestDTO shipmentRequest) {
        ShipmentResponseDTO shipmentDTO = shipmentService.createShipment(shipmentRequest);
        return new ResponseEntity<>(shipmentDTO, HttpStatus.CREATED);
    }        //ResponseEntity = what was processed (data)+ if the request was successful + Any extra header details (security, caching info etc)

    // get Shipment based on ID.
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable Long id) {
        Optional<ShipmentResponseDTO> shipmentresponse =
                Optional.ofNullable(shipmentService.getShipmentById(id));// wrapping response in Optional
        return shipmentresponse
                .map(shipmentData -> new ResponseEntity<>(shipmentData, HttpStatus.OK)) // 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 if it's a null
    }

    //update shipment
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> updateShipment(@PathVariable Long id, @RequestBody CreateShipmentRequestDTO shipmentRequest){
        Optional<ShipmentResponseDTO> updatedShipment = shipmentService.updateShipment(id, shipmentRequest);
        return updatedShipment
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // delete shipment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        boolean deleted = shipmentService.deleteShipment(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

