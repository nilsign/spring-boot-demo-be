package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
  private DtoArrayList<DeliveryDto> deliveries;

  @Getter @Setter @NotNull @NotEmpty
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
