package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController extends AbstractController<DeliveryDto, DeliveryEntity, Long> {
  @Autowired
  private DeliveryService deliveryService;

  @Override
  protected DeliveryService getService() {
    return deliveryService;
  }

  @Override
  protected DeliveryEntity entityFromDto(DeliveryDto dto) {
    return DeliveryEntity.fromDto(dto);
  }

  @Override
  protected DeliveryDto dtoFromEntity(DeliveryEntity entity) {
    return DeliveryDto.fromEntity(entity);
  }
}
