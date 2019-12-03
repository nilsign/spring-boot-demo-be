package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;

import java.util.Set;

public interface ProductRepositoryExtended {

  Set<ProductEntity> findByOrderId(Long orderId);

  Set<ProductEntity> byMultipleIds(Set<Long> productIds);
}
