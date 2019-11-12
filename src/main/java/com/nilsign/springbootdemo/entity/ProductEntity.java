package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_product")
public class ProductEntity {
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Getter @Setter
  @Column(name="name")
  private String name;

  @Getter @Setter
  @Column(name="price")
  private BigDecimal price;
}
