package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class DeliveryDto implements AbstractDto {
  private Long id;

  @NonNull
  private AddressDto deliveryAddress;

  @Override
  public DeliveryEntity toEntity() {
    DeliveryEntity entity = new DeliveryEntity(deliveryAddress.toEntity());
    entity.setId(id);
    return entity;
  }
}
