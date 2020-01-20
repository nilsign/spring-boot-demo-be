package com.nilsign.springbootdemo.domain.order.repository;

import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends OrderRepositoryExtended, JpaRepository<OrderEntity, Long> {
}
