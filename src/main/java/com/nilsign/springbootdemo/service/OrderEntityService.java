package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.repository.OrderRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Service
public class OrderEntityService extends EntityService<OrderEntity, Long> {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  protected OrderRepository getRepository() {
    return orderRepository;
  }

  public List<OrderEntity> findByProductId(@NotNull @Positive Long productId) {
    return orderRepository.findByProductId(productId);
  }
}
