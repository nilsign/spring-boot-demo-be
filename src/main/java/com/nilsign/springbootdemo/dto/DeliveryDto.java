package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class DeliveryDto {
  @Getter
  @Setter
  private Long id;

  @Getter
  @Setter
  private AddressDto deliveryAddress;
}
