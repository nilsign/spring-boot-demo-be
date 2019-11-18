package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
public class OrderDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private UserDto user;

  @Getter @Setter
  private AddressDto invoiceAddress;

  @Getter @Setter
  private List<DeliveryDto> deliveries;

  @Getter @Setter
  private List<ProductDto> products;
}
