package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.OrderEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public interface OrderRepositoryExtended {

  List<OrderEntity> findByProductId( @NotNull @Positive Long productId);

  List<OrderEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> orderIds);
}
