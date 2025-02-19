package com.pragra.shippingapplication.controller;


import com.pragra.shippingapplication.model.Shipment;
import com.pragra.shippingapplication.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    //create a new Shipment
    @PostMapping
    public Shipment createShipment(@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.createShipment(orderId, userId);
    }

    // get Shipment based on ID.
    @GetMapping("/shipment/{id}")
    public Shipment getShipmentById(@PathVariable Long id){
        return shipmentService.getShipmentById(id);
    }

    //update shipment
    @PutMapping("/{id}")
    public Shipment updateShipment(@PathVariable Long id,@RequestParam Long orderId, @RequestParam Long userId) {
        return shipmentService.updateShipment(id, orderId, userId);
    }// Must create options with just id and orderId for when we don't have user id at hand.


    // delete shipment
    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
    }

}
