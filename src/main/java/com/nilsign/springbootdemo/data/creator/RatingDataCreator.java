package com.nilsign.springbootdemo.data.creator;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.ProductEntityService;
import com.nilsign.springbootdemo.service.RatingEntityService;
import com.nilsign.springbootdemo.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Service
public final class RatingDataCreator {

  @Autowired
  private RatingEntityService ratingEntityService;

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private ProductEntityService productEntityService;

  public void createRatingIfNotExist(
      @NotNull UserEntity userEntity,
      @NotNull ProductEntity productEntity,
      @NotNull @DecimalMin("0.0") @DecimalMax("5.0") float score,
      String description) {
    ratingEntityService.save(RatingEntity.builder()
        .user(userEntity)
        .product(productEntity)
        .score(score)
        .description(description)
        .build());
  }

  public void createRatingIfNotExist(
      @NotNull @NotBlank @Email String email,
      @NotNull @Positive Integer productNumber,
      @NotNull @DecimalMin("0.0") @DecimalMax("5.0") float score,
      String description) {
    userEntityService.findByEmail(email).ifPresent(userEntity ->
        productEntityService.findByProductNumber(productNumber).ifPresent(productEntity -> {
          if (ratingEntityService.findByProductAndUser(productEntity, userEntity).isEmpty()) {
            createRatingIfNotExist(userEntity, productEntity, score, description);
          }
    }));
  }
}
