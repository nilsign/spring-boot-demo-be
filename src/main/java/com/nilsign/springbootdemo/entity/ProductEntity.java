package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_product")
public class ProductEntity extends SequencedEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

// TODO(nilsheumer): Reintroduce once the entities below are reintroduced.
//  // Bi-directional one-to-many relation.
//  @OneToMany(
//      mappedBy = "product",
//      cascade = {
//          CascadeType.DETACH,
//          CascadeType.MERGE,
//          CascadeType.PERSIST,
//          CascadeType.REFRESH})
//  private List<RatingEntity> ratings;
//
//  // Bi-directional many-to-many relation.
//  @ManyToMany(
//      mappedBy = "products",
//      cascade = {
//          CascadeType.DETACH,
//          CascadeType.MERGE,
//          CascadeType.PERSIST,
//          CascadeType.REFRESH})
//  @JsonBackReference // Move back reference to dto?
//  private Set<OrderEntity> orders;

  public static ProductEntity create(ProductDto productDto) {
    return ProductEntity.builder()
        .id(productDto.getId())
        .name(productDto.getName())
        .price(productDto.getPrice())
        // TODO(nilsheumer): Find a solution for this problem. Use refresh, or is this loaded lazy
        //  anyways?
        // .ratings(productDto.getRatings()
        //     .stream()
        //     .map(RatingEntity::create)
        //     .collect(Collectors.toList()))
        // .orders(productDto.getOrders()
        //     .stream()
        //     .map(OrderEntity::create)
        //      .collect(Collectors.toSet()))
        .build();
  }
}
