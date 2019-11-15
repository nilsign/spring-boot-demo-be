package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@ToString
@Entity
@Table(name = "tbl_order")
public class OrderEntity extends AbstractEntity {
  @Getter @Setter
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Getter @Setter
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
  @JoinColumn(name = "invoice_address_id", nullable = false)
  private AddressEntity invoiceAddress;

  @Getter @Setter
  @ManyToOne(
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH})
  @JoinColumn(name = "delivery_address_id", nullable = false)
  private AddressEntity deliveryAddress;

  @Getter @Setter
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
  @JoinTable(
      name= "tbl_order_tbl_product",
      joinColumns = @JoinColumn(
          name = "order_id",
          referencedColumnName = "id",
          nullable = false),
      inverseJoinColumns = @JoinColumn(
          name = "product_id",
          referencedColumnName = "id",
          nullable = false))
  private List<ProductEntity> products;
}