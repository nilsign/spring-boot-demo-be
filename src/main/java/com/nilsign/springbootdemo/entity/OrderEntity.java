package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_order")
public class OrderEntity extends AbstractEntity {
  // Uni-directional many-to-one relation.
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

  // Uni-directional many-to-one relation.
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

  // Uni-directional one-to-many relation.
  @Getter @Setter
  @OneToMany(
      mappedBy="tbl_order",
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private List<DeliveryEntity> deliveries;

  // Bi-directional many-to-many relation.
  @Getter @Setter
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
  @JoinTable(
      name = "tbl_order_tbl_product",
      joinColumns = @JoinColumn(
          name = "order_id",
          referencedColumnName = "id",
          nullable = false),
      inverseJoinColumns = @JoinColumn(
          name = "product_id",
          referencedColumnName = "id",
          nullable = false))
  private List<ProductEntity> products;

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + OrderEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "user=" + user)
        .add("\n\t" + "invoiceAddress=" + invoiceAddress)
        .add("\n\t" + "products=" + (products == null ? null : products.size()))
        .toString();
  }
}
