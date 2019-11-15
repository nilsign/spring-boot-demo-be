package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController extends AbstractController<OrderEntity, Long> {
  @Autowired
  private OrderService orderService;

  @Override
  protected OrderService getService() {
    return orderService;
  }
}
