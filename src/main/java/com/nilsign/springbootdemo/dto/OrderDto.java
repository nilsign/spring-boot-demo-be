package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class OrderDto implements AbstractDto {
  private Long id;

  @NotNull
  private UserDto user;

  @NotNull
  private AddressDto invoiceAddress;

  @NotNull
  @NotEmpty
  private DtoArrayList<DeliveryDto> deliveries;

  @NotNull
  @NotEmpty
  private DtoArrayList<ProductDto> products;

  @Override
  public OrderEntity toEntity() {
    return OrderEntity.builder()
        .id(id)
        .user(user.toEntity())
        .invoiceAddress(invoiceAddress.toEntity())
        .deliveries(deliveries.toEntities())
        .products(products.toEntities())
        .build();
  }
}
