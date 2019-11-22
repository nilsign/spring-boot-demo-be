package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.RatingDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_rating")
public class RatingEntity extends AbstractEntity {
  // Uni-directional many-to-one relation.
  @Getter @Setter
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
  @Getter @Setter
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @Getter @Setter
  @Column(name = "score")
  private float score;

  @Setter @Getter
  @Column(name = "description")
  private String description;

  public static RatingEntity fromDto(RatingDto dto) {
    RatingEntity entity = new RatingEntity();
    entity.setId(dto.getId());
    entity.setUser(UserEntity.fromDto(dto.getUser()));
    entity.setProduct(ProductEntity.fromDto(dto.getProduct()));
    entity.setScore(dto.getScore());
    entity.setDescription(dto.getDescription());
    return entity;
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
