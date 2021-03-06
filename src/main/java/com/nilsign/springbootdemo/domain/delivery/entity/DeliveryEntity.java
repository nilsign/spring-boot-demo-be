package com.nilsign.springbootdemo.domain.delivery.entity;

import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.delivery.dto.DeliveryDto;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_delivery")
public class DeliveryEntity extends SequencedEntity {

  // Uni-directional many-to-one relation.
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "address_id", nullable = false)
  private AddressEntity deliveryAddress;

  // Bi-directional many-to-one relation.
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  public static DeliveryEntity create(@NotNull DeliveryDto deliveryDto) {
    return DeliveryEntity.builder()
        .id(deliveryDto.getId())
        .deliveryAddress(AddressEntity.create(deliveryDto.getDeliveryAddress()))
        .build();
  }
}
