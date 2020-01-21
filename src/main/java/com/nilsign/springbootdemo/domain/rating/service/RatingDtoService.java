package com.nilsign.springbootdemo.domain.rating.service;

import com.nilsign.springbootdemo.domain.product.service.ProductEntityService;
import com.nilsign.springbootdemo.domain.rating.dto.RatingDto;
import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.rating.entity.RatingEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import com.nilsign.springbootdemo.domain.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RatingDtoService extends DtoService<RatingDto, RatingEntity, Long> {

  @Autowired
  private RatingEntityService ratingEntityService;

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private ProductEntityService productEntityService;

  @Override
  protected RatingEntityService getEntityService() {
    return ratingEntityService;
  }

  @Override
  protected RatingEntity toEntity(@NotNull RatingDto ratingDto) {
    UserEntity userEntity = userEntityService.findById(ratingDto.getUser().getId())
        .orElseThrow(() -> new IllegalStateException(String.format(
            "RatingDto has an unknown user id '%d'. UserEntity can not be null.",
            ratingDto.getUser().getId())));
    ProductEntity productEntity = productEntityService.findById(ratingDto.getProductId())
        .orElseThrow(() -> new IllegalStateException(String.format(
            "RatingDto has an unknown product id '%d'. ProductEntity can not be null.",
            ratingDto.getProductId())));
    return RatingEntity.create(
        ratingDto,
        userEntity,
        productEntity);
  }

  @Override
  protected RatingDto toDto(@NotNull RatingEntity ratingEntity) {
    return RatingDto.create(ratingEntity);
  }
}
