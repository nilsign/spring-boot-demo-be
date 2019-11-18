package com.nilsign.springbootdemo.entity;

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

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + DeliveryEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\tid='" + super.getId() + "'")
        .add("\n\t" + "deliveryAddress=" + deliveryAddress)
        .toString();
  }
}