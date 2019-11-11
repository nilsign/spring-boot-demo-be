package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Getter @Setter
  @Column(name="first_name")
  private String firstName;

  @Getter @Setter
  @Column(name="last_name")
  private String lastName;

  @Getter @Setter
  @Column(name="email")
  private String email;
}
