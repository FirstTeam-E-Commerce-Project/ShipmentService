package com.pragra.shippingapplication.repository;

import com.pragra.shippingapplication.model.Shipment;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {


}
