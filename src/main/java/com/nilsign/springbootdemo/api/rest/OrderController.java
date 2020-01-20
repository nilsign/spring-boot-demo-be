package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.order.dto.OrderDto;
import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import com.nilsign.springbootdemo.domain.order.service.OrderDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController extends Controller<OrderDto, OrderEntity, Long> {

  @Autowired
  private OrderDtoService orderDtoService;

  @Override
  protected OrderDtoService getDtoService() {
    return orderDtoService;
  }
}
