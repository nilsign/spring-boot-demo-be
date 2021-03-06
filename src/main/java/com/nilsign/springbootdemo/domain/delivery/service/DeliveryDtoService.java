package com.nilsign.springbootdemo.domain.delivery.service;

import com.nilsign.springbootdemo.domain.delivery.dto.DeliveryDto;
import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import com.nilsign.springbootdemo.domain.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class DeliveryDtoService extends DtoService<DeliveryDto, DeliveryEntity, Long> {

  @Autowired
  private DeliveryEntityService deliveryEntityService;

  @Override
  protected DeliveryEntityService getEntityService() {
    return deliveryEntityService;
  }

  @Override
  protected DeliveryEntity toEntity(@NotNull DeliveryDto deliveryDto) {
    return DeliveryEntity.create(deliveryDto);
  }

  @Override
  protected DeliveryDto toDto(@NotNull DeliveryEntity deliveryEntity) {
    return DeliveryDto.create(deliveryEntity);
  }
}
