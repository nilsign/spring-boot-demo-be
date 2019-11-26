package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.RatingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_rating")
public class RatingEntity extends AbstractEntity {
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

  // Bi-directional many-to-one relation.
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @Column(name = "score")
  private float score;

  @Column(name = "description")
  private String description;

  @Override
  public RatingDto toDto() {
    return new RatingDto(
        super.getId(),
        user.toDto(),
        product.toDto(),
        score,
        description);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + RatingEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id=" + super.getId())
        .add("\n\t" + "user=" + user)
        .add("\n\t" + "product=" + product)
        .add("\n\t" + "score=" + score)
        .add("\n\t" + "description='" + description + "'")
        .toString();
  }
}
