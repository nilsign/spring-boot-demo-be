package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

public interface ProductRepositoryExtended {

  Set<ProductEntity> findByOrderId(
      @NotNull @Positive Long orderId);

  Set<ProductEntity> byMultipleIds(
      @NotNull @NotEmpty Set<Long> productIds);
}
