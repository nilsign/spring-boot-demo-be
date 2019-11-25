package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.DeliveryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@NoArgsConstructor
@AllArgsConstructor
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
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "deliveryAddress=" + deliveryAddress)
        .toString();
  }

  @Override
  public DeliveryDto toDto() {
    return new DeliveryDto(super.getId(), toDto(deliveryAddress));
  }
}
