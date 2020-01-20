package com.nilsign.springbootdemo.domain.order.service;

import com.nilsign.springbootdemo.domain.order.dto.OrderDto;
import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.product.service.ProductEntityService;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    UserEntity userEntity = userEntityService.findById(orderDto.getUser().getId())
        .orElseThrow(() -> new IllegalStateException((String.format(
            "OrderDto has an unknown user id '%d'. UserEntity can not be null.",
            orderDto.getUser().getId()))));
    List<ProductEntity> productEntities = productEntityService.findByOrderId(orderDto.getId());
    return OrderEntity.create(orderDto, userEntity, productEntities);
  }

  @Override
  protected OrderDto toDto(@NotNull OrderEntity orderEntity) {
    return OrderDto.create(orderEntity);
  }
}
