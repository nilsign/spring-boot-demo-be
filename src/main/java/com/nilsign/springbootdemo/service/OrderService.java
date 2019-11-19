package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractService<OrderEntity, Long> {
  private OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  protected OrderRepository getRepository() {
    return orderRepository;
  }
}
