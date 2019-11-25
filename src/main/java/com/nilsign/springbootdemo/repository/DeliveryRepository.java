package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
