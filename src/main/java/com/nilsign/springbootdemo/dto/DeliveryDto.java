package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class DeliveryDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NonNull
  private AddressDto deliveryAddress;

  @Override
  public DeliveryEntity toEntity() {
     DeliveryEntity entity =  new DeliveryEntity(deliveryAddress.toEntity());
     entity.setId(id);
     return entity;
  }
}
