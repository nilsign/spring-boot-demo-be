package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.CustomerDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
}
