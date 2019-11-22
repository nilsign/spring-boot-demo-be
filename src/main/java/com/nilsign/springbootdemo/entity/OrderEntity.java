package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.OrderDto;
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
import java.util.stream.Collectors;

// TODO(nilsheumer): Research what the lombok @Data annotation really does and whether/where it can
// be used properly.
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
      fetch = FetchType.LAZY,
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


  public static OrderEntity fromDto(OrderDto dto) {
    OrderEntity entity = new OrderEntity();
    entity.setId(dto.getId());
    entity.setInvoiceAddress(AddressEntity.fromDto(dto.getInvoiceAddress()));
    entity.setDeliveries(dto.getDeliveries()
        .stream()
        .map(DeliveryEntity::fromDto)
        .collect(Collectors.toList())
    );
    entity.setUser(UserEntity.fromDto(dto.getUser()));
    entity.setProducts(dto.getProducts()
        .stream()
        .map(ProductEntity::fromDto)
        .collect(Collectors.toList()));
    return entity;
  }

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
