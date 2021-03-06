package com.nilsign.springbootdemo.domain.customer.entity;

import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.customer.dto.CustomerDto;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
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
import javax.validation.constraints.NotNull;

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
      fetch = FetchType.LAZY,
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
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "postal_address_id")
  private AddressEntity postalAddress;

  public static CustomerEntity create(@NotNull CustomerDto customerDto) {
    return CustomerEntity.builder()
        .user(UserEntity.create(
            customerDto.getUser(),
            CustomerEntity.create(customerDto)))
        .termsAndConditionsAccepted(customerDto.isTermsAndConditionsAccepted())
        .postalAddress(AddressEntity.create(customerDto.getPostalAddress()))
        .build();
  }
}
