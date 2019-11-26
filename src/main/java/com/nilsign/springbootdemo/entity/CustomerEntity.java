package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.CustomerDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_customer")
public class CustomerEntity extends SequencedEntity {
  // Bi-directional one-to-one relation.
  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(name = "terms_and_conditions_accepted", nullable = false)
  private boolean termsAndConditionsAccepted;

  // Uni-directional one-to-one relation.
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
    return CustomerDto.builder()
        .id(super.getId())
        .user(user.toDto())
        .termsAndConditionsAccepted(termsAndConditionsAccepted)
        .postalAddress(postalAddress.toDto())
        .build();
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
