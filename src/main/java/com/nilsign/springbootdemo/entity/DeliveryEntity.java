package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_delivery")
public class DeliveryEntity extends AbstractEntity {
  // Uni-directional delivery address.
  @Getter @Setter
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "delivery_address_id", nullable = false)
  private AddressEntity deliveryAddress;

  public static DeliveryEntity fromDto(DeliveryDto dto) {
    DeliveryEntity entity =  new DeliveryEntity();
    entity.setId(dto.getId());
    entity.setDeliveryAddress(AddressEntity.fromDto(dto.getDeliveryAddress()));
    return entity;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + DeliveryEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "deliveryAddress=" + deliveryAddress)
        .toString();
  }
}
