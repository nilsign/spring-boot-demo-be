package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_order")
public class OrderEntity extends SequencedEntity {
  // Uni-directional many-to-one relation.
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
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private List<DeliveryEntity> deliveries;

  // Bi-directional many-to-many relation.
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

  public void addDelivery(DeliveryEntity delivery) {
    if (deliveries == null) {
      deliveries = new EntityArrayList<>();
    }
    deliveries.add(delivery);
  }

  public void addProduct(ProductEntity product) {
    if (products == null) {
      products = new EntityArrayList<>();
    }
    products.add(product);
  }

  @Override
  public OrderDto toDto() {
    return OrderDto.builder()
        .id(super.getId())
        .user(user.toDto())
        .invoiceAddress(invoiceAddress.toDto())
        .deliveries(toDtoArrayList(deliveries))
        .products(toDtoArrayList(products))
        .build();
  }
}
