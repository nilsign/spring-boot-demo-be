package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class OrderDto implements Dto {

  private Long id;

  @NotNull
  private UserDto user;

  @NotNull
  private AddressDto invoiceAddress;

  @NotNull
  @NotEmpty
  private List<DeliveryDto> deliveries;

  @NotNull
  @NotEmpty
  private Set<ProductDto> products;

  public static OrderDto create(OrderEntity orderEntity) {
    return OrderDto.builder()
        .id(orderEntity.getId())
        .user(UserDto.create(orderEntity.getUser()))
        .invoiceAddress(AddressDto.create(orderEntity.getInvoiceAddress()))
        .deliveries(orderEntity.getDeliveries()
            .stream()
            .map(DeliveryDto::create)
            .collect(Collectors.toList()))
        .products(orderEntity.getProducts()
            .stream()
            .map(ProductDto::create)
            .collect(Collectors.toSet()))
        .build();
  }
}
