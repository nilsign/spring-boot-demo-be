package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

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
  protected OrderEntity toEntity(@NotNull OrderDto orderDto) {
    Optional<UserEntity> userEntity = userEntityService.findById(orderDto.getUser().getId());
    List<ProductEntity> productEntities = productEntityService.findByOrderId(orderDto.getId());
    return OrderEntity.create(orderDto, userEntity.get(), productEntities);
  }

  @Override
  protected OrderDto toDto(@NotNull OrderEntity orderEntity) {
    return OrderDto.create(orderEntity);
  }
}
