package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@AllArgsConstructor
public class OrderDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull
  private UserDto user;

  @Getter @Setter @NotNull
  private AddressDto invoiceAddress;

  @Getter @Setter @NotNull @NotEmpty
  private List<DeliveryDto> deliveries;

  @Getter @Setter @NotNull @NotEmpty
  private List<ProductDto> products;
}
