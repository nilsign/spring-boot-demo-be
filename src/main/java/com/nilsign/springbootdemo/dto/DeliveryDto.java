package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.DeliveryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class DeliveryDto implements Dto {

  private Long id;

  @NonNull
  private AddressDto deliveryAddress;

  public static DeliveryDto create(DeliveryEntity deliveryEntity) {
    return DeliveryDto.builder()
        .id(deliveryEntity.getId())
        .deliveryAddress(AddressDto.create(deliveryEntity.getDeliveryAddress()))
        .build();
  }
}
