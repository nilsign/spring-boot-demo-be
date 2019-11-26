package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.AddressDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_address")
public class AddressEntity extends SequencedEntity {
  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "zip", nullable = false)
  private String zip;

  @Column(name = "country", nullable = false)
  private String country;

  @Override
  public AddressDto toDto() {
    return AddressDto.builder()
        .id(super.getId())
        .address(address)
        .city(city)
        .zip(zip)
        .country(country)
        .build();
  }
}
