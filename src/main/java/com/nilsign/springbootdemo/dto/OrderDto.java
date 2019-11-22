package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

  public static OrderDto fromEntity(OrderEntity entity) {
    return new OrderDto(
        entity.getId(),
        UserDto.fromEntity(entity.getUser()),
        AddressDto.fromEntity(entity.getInvoiceAddress()),
        entity.getDeliveries()
            .stream()
            .map(DeliveryDto::fromEntity)
            .collect(Collectors.toList()),
        entity.getProducts()
            .stream()
            .map(ProductDto::fromEntity)
            .collect(Collectors.toList()));
  }
}
