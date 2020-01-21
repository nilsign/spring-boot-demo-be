package com.nilsign.springbootdemo.domain.delivery.dto;

import com.nilsign.springbootdemo.domain.Dto;
import com.nilsign.springbootdemo.domain.address.dto.AddressDto;
import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class DeliveryDto implements Dto {

  private Long id;

  @NonNull
  private AddressDto deliveryAddress;

  public static DeliveryDto create(@NotNull DeliveryEntity deliveryEntity) {
    return DeliveryDto.builder()
        .id(deliveryEntity.getId())
        .deliveryAddress(AddressDto.create(deliveryEntity.getDeliveryAddress()))
        .build();
  }
}
