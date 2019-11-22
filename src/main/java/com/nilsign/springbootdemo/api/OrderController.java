package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/order")
public class OrderController extends AbstractController<OrderDto, OrderEntity, Long> {
  private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  protected OrderService getService() {
    return orderService;
  }

  @Override
  protected OrderEntity entityFromDto(OrderDto dto) {
    return OrderController.orderEntityFromDto(dto);
  }

  @Override
  protected OrderDto dtoFromEntity(OrderEntity entity) {
    return OrderController.orderDtoFromEntity(entity);
  }

  public static OrderEntity orderEntityFromDto(OrderDto dto) {
    OrderEntity entity = new OrderEntity();
    entity.setId(dto.getId());
    entity.setInvoiceAddress(AddressController.addressEntityFromDto(dto.getInvoiceAddress()));
    entity.setDeliveries(dto.getDeliveries()
        .stream()
        .map(DeliveryController::deliveryEntityFromDto)
        .collect(Collectors.toList())
    );
    entity.setUser(UserController.userEntityFromDto(dto.getUser()));
    entity.setProducts(dto.getProducts()
        .stream()
        .map(ProductController::productEntityFromDto)
        .collect(Collectors.toList()));
    return entity;
  }

  // TODO(nilsheumer): Move all dtoFromEntity function to their according to Dto classes.
  public static OrderDto orderDtoFromEntity(OrderEntity entity) {
    return new OrderDto(
        entity.getId(),
        UserController.userDtoFromEntity(entity.getUser()),
        AddressController.addressDtoFromEntity(entity.getInvoiceAddress()),
        entity.getDeliveries()
            .stream()
            .map(DeliveryController::deliveryDtoFromEntity)
            .collect(Collectors.toList()),
        entity.getProducts()
            .stream()
            .map(ProductController::productDtoFromEntity)
            .collect(Collectors.toList()));
  }
}
