package com.nilsign.springbootdemo.domain.delivery.repository;

import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
