package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_address")
public class AddressEntity extends AbstractEntity {
  @Getter @Setter
  @Column(name="address", nullable = false)
  private String address;

  @Getter @Setter
  @Column(name="city", nullable = false)
  private String city;

  @Getter @Setter
  @Column(name="zip", nullable = false)
  private String zip;

  @Getter @Setter
  @Column(name="country", nullable = false)
  private String country;

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
