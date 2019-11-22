package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController extends AbstractController<OrderDto, OrderEntity, Long> {
  @Autowired
  private OrderService orderService;

  @Override
  protected OrderService getService() {
    return orderService;
  }

  @Override
  protected OrderEntity entityFromDto(OrderDto dto) {
    return OrderEntity.fromDto(dto);
  }

  @Override
  protected OrderDto dtoFromEntity(OrderEntity entity) {
    return OrderDto.fromEntity(entity);
  }
}
