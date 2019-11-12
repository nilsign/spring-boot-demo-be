package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_address")
public class AddressEntity extends AbstractEntity {
  @Getter
  @Setter
  @Column(name="address")
  private String address;

  @Getter
  @Setter
  @Column(name="city")
  private String city;

  @Getter
  @Setter
  @Column(name="zip")
  private String zip;

  @Getter
  @Setter
  @Column(name="country")
  private String country;
}
