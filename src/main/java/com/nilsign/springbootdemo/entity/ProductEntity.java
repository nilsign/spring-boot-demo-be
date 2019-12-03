package com.nilsign.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_product")
public class ProductEntity extends SequencedEntity {

  @Column(name = "product_number", unique = true, nullable = false)
  private Integer productNumber;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  // Bi-directional one-to-many relation.
  @OneToMany(
      mappedBy = "product",
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private List<RatingEntity> ratings;

  // Bi-directional many-to-many relation.
  @ManyToMany(
      mappedBy = "products",
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  // TODO(nilsheumer): Shouldn't this move to the according dto? Test and move when true.
  @JsonBackReference
  private Set<OrderEntity> orders;

  public static ProductEntity create(
      ProductDto productDto,
      List<RatingEntity> ratings,
      Set<OrderEntity> orders) {
    return ProductEntity.builder()
        .id(productDto.getId())
        .productNumber(productDto.getProductNumber())
        .productName(productDto.getProductName())
        .price(productDto.getPrice())
        .ratings(ratings)
        .orders(orders)
        .build();
  }
}
