package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.service.DeliveryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController extends AbstractController<DeliveryDto, DeliveryEntity, Long> {
  private DeliveryService deliveryService;

  public DeliveryController(DeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @Override
  protected DeliveryService getService() {
    return deliveryService;
  }

  @Override
  protected DeliveryEntity entityFromDto(DeliveryDto dto) {
    return DeliveryController.deliveryEntityFromDto(dto);
  }

  @Override
  protected DeliveryDto dtoFromEntity(DeliveryEntity entity) {
    return DeliveryController.deliveryDtoFromEntity(entity);
  }

  public static DeliveryEntity deliveryEntityFromDto(DeliveryDto dto) {
    DeliveryEntity entity =  new DeliveryEntity();
    entity.setId(dto.getId());
    entity.setDeliveryAddress(AddressController.addressEntityFromDto(dto.getDeliveryAddress()));
    return entity;
  }

  public static DeliveryDto deliveryDtoFromEntity(DeliveryEntity entity) {
    return new DeliveryDto(
        entity.getId(),
        AddressController.addressDtoFromEntity(entity.getDeliveryAddress()));
  }
}
