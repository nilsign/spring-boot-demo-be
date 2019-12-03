package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
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

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_delivery")
public class DeliveryEntity extends SequencedEntity {

  // Uni-directional delivery address.
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "delivery_address_id", nullable = false)
  private AddressEntity deliveryAddress;

  public static DeliveryEntity create(DeliveryDto deliveryDto) {
    return DeliveryEntity.builder()
        .id(deliveryDto.getId())
        .deliveryAddress(AddressEntity.create(deliveryDto.getDeliveryAddress()))
        .build();
  }
}
