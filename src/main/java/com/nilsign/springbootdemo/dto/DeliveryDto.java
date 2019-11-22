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

  public static DeliveryDto fromEntity(DeliveryEntity entity) {
    return new DeliveryDto(
        entity.getId(),
        AddressDto.fromEntity(entity.getDeliveryAddress()));
  }
}
