package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class OrderDtoService extends DtoService<OrderDto, OrderEntity, Long> {

  @Autowired
  private OrderEntityService orderEntityService;

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private ProductEntityService productEntityService;

  @Override
  protected EntityService<OrderEntity, Long> getEntityService() {
    return orderEntityService;
  }

  @Override
  protected OrderEntity toEntity(OrderDto orderDto) {
    Optional<UserEntity> userEntity = userEntityService.findById(orderDto.getUser().getId());
    Set<ProductEntity> productEntities = productEntityService.findByOrderId(orderDto.getId());
    return OrderEntity.create(orderDto, userEntity.get(), productEntities);
  }

  @Override
  protected OrderDto toDto(OrderEntity orderEntity) {
    return OrderDto.create(orderEntity);
  }
}
