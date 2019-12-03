package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.OrderEntity;

import java.util.Set;

public interface OrderRepositoryExtended {

  Set<OrderEntity> findByProductId(Long productId);

  Set<OrderEntity> byMultipleIds(Set<Long> orderIds);
}
