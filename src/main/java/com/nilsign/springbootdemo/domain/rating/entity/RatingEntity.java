package com.nilsign.springbootdemo.domain.rating.entity;

import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.rating.dto.RatingDto;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_rating")
public class RatingEntity extends SequencedEntity {

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
      fetch = FetchType.LAZY,
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

  public static RatingEntity create(
      @NotNull RatingDto ratingDto,
      @NotNull UserEntity userEntity,
      @NotNull ProductEntity productEntity) {
    return RatingEntity.builder()
        .id(ratingDto.getId())
        .user(userEntity)
        .product(productEntity)
        .score(ratingDto.getScore())
        .description(ratingDto.getDescription())
        .build();
  }
}
