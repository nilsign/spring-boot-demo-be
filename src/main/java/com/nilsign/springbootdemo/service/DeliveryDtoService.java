package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
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

  // Here as well. See comment at AddressDtoService
  @Override
  protected DeliveryEntity toEntity(@NotNull DeliveryDto deliveryDto) {
    return DeliveryEntity.create(deliveryDto);
  }

  @Override
  protected DeliveryDto toDto(@NotNull DeliveryEntity deliveryEntity) {
    return DeliveryDto.create(deliveryEntity);
  }
}
