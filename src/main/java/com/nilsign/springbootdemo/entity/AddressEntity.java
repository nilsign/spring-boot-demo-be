package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.AddressDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

// TODO(nilsheumer): Test whether this properly renders the all field including all base class
//  fields. If yes, replace all to string methods by this using the annotation below.
// @ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_address")
public class AddressEntity extends AbstractEntity {
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

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + AddressEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "address='" + address + "'")
        .add("\n\t" + "city='" + city + "'")
        .add("\n\t" + "zip='" + zip + "'")
        .add("\n\t" + "country='" + country + "'")
        .toString();
  }
}
