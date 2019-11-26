package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class DeliveryDto implements AbstractDto {
  private Long id;

  @NonNull
  private AddressDto deliveryAddress;

  @Override
  public DeliveryEntity toEntity() {
    return DeliveryEntity.builder()
        .id(id)
        .deliveryAddress(deliveryAddress.toEntity())
        .build();
  }
}
