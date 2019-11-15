package com.nilsign.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@ToString
@Entity
@Table(name = "tbl_product")
public class ProductEntity extends AbstractEntity {
  @Getter @Setter
  @Column(name="name", nullable = false)
  private String name;

  @Getter @Setter
  @Column(name="price", nullable = false)
  private BigDecimal price;

  // TODO(nilsheumer): Find out why orders is not displayed in Postmans get response.
  @Getter @Setter
  @ManyToMany(
      // TODO(nilsheumer): Find out if and if a mappedBy is meaningful here.
      // mappedBy = "products",
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinTable(
      name= "tbl_order_tbl_product",
      joinColumns = @JoinColumn(
          name = "product_id",
          referencedColumnName = "id",
          nullable = false),
      inverseJoinColumns = @JoinColumn(
          name = "order_id",
          referencedColumnName = "id",
          nullable = false))
  @JsonBackReference
  private List<OrderEntity> orders;
}
