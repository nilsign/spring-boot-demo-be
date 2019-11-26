package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
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
    OrderEntity entity = new OrderEntity(
        user.toEntity(),
        invoiceAddress.toEntity(),
        deliveries.toEntities(),
        products.toEntities());
    entity.setId(id);
    return entity;
  }
}
