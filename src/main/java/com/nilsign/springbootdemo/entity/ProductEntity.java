package com.nilsign.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_product")
public class ProductEntity extends AbstractEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  // Bi-directional one-to-many relation.
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

  public void addRating(RatingEntity rating) {
    if (ratings == null) {
      ratings = new EntityArrayList<>();
    }
    ratings.add(rating);
  }

  public void addOrder(OrderEntity order) {
    if (orders == null) {
      orders = new EntityArrayList<>();
    }
    orders.add(order);
  }

  @Override
  public ProductDto toDto() {
    return new ProductDto(
        super.getId(),
        name,
        price,
        toDtoArrayList(ratings),
        toDtoArrayList(orders));
  }

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
