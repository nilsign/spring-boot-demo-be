package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.RatingDto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
  protected RatingEntity toEntity(RatingDto ratingDto) {
    Optional<UserEntity> userEntity = userEntityService.findById(ratingDto.getUser().getId());
    Optional<ProductEntity> productEntity = productEntityService.findById(
        ratingDto.getProductId());
    return RatingEntity.create(ratingDto, userEntity.get(), productEntity.get());
  }

  @Override
  protected RatingDto toDto(RatingEntity ratingEntity) {
    return RatingDto.create(ratingEntity);
  }
}
