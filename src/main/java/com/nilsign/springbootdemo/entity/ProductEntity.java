package com.nilsign.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_product")
public class ProductEntity extends AbstractEntity {
  @Getter @Setter
  @Column(name="name", nullable = false)
  private String name;

  @Getter @Setter
  @Column(name="price", nullable = false)
  private BigDecimal price;

  // Bi-directional one-to-many relation.
  @Getter @Setter
  @OneToMany(
      mappedBy = "product",
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private List<RatingEntity> ratings;

  // Bi-directional many-to-many relation.
  @Getter @Setter
  @ManyToMany(
      mappedBy = "products",
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JsonBackReference
  private List<OrderEntity> orders;

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + ProductEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "name='" + name + "'")
        .add("\n\t" + "price=" + price)
        .add("\n\t" + "ratings=" + (ratings == null ? null : ratings.size()))
        .add("\n\t" + "orders=" + (orders == null ? null : orders.size()))
        .toString();
  }
}
