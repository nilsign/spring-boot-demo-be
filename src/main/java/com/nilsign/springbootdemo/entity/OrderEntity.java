package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.OrderDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_order")
public class OrderEntity extends SequencedEntity {
  // Uni-directional many-to-one relation.
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  // Uni-directional many-to-one relation.
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "invoice_address_id", nullable = false)
  private AddressEntity invoiceAddress;

  // Bi-directional one-to-many relation.
  @OneToMany(
      mappedBy = "order",
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private List<DeliveryEntity> deliveries;

  // Bi-directional many-to-many relation.
  @ManyToMany(
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
  private Set<ProductEntity> products;

  public static OrderEntity create(
      OrderDto orderDto,
      UserEntity userEntity,
      Set<ProductEntity> products) {
    return OrderEntity.builder()
        .id(orderDto.getId())
        .user(userEntity)
        .invoiceAddress(AddressEntity.create(orderDto.getInvoiceAddress()))
        .deliveries(orderDto.getDeliveries()
            .stream()
            .map(DeliveryEntity::create)
            .collect(Collectors.toList()))
        .products(products)
        .build();
  }
}
