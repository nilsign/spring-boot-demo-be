package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryDtoService extends DtoService<DeliveryDto, DeliveryEntity, Long> {

  @Autowired
  private DeliveryEntityService deliveryEntityService;

  @Override
  protected DeliveryEntityService getEntityService() {
    return deliveryEntityService;
  }

  @Override
  protected DeliveryEntity toEntity(DeliveryDto deliveryDto) {
    return DeliveryEntity.create(deliveryDto);
  }

  @Override
  protected DeliveryDto toDto(DeliveryEntity deliveryEntity) {
    return DeliveryDto.create(deliveryEntity);
  }
}
