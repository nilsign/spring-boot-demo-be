package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@AllArgsConstructor
@Entity
@Table(name = "tbl_customer")
public class CustomerEntity extends AbstractEntity {
  // Bi-directional one-to-one relation.
  @Getter @Setter
  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Getter @Setter
  @Column(name = "terms_and_conditions_accepted", nullable = false)
  private boolean termsAndConditionsAccepted;

  // Uni-directional one-to-one relation.
  @Getter @Setter
  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "postal_address_id")
  private AddressEntity postalAddress;

  @Override
  public CustomerDto toDto() {
    return new CustomerDto(
      super.getId(),
      user.toDto(),
      termsAndConditionsAccepted,
      postalAddress.toDto());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + CustomerEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id=" + super.getId())
        .add("\n\t" + "user=" + user)
        .add("\n\t" + "postalAddress=" + postalAddress)
        .add("\n\t" + "termsAndConditionsAccepted=" + termsAndConditionsAccepted)
        .toString();
  }
}
