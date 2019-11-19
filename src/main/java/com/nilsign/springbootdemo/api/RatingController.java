package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.RatingDto;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.RatingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController extends AbstractController<RatingDto, RatingEntity, Long> {
  private RatingService ratingService;

  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  protected RatingService getService() {
    return ratingService;
  }

  @Override
  protected RatingEntity entityFromDto(RatingDto dto) {
    return RatingController.ratingEntityFromDto(dto);
  }

  @Override
  protected RatingDto dtoFromEntity(RatingEntity entity) {
    return RatingController.ratingDtoFromEntity(entity);
  }

  public static RatingEntity ratingEntityFromDto(RatingDto dto) {
    RatingEntity entity = new RatingEntity();
    entity.setId(dto.getId());
    entity.setUser(UserController.userEntityFromDto(dto.getUser()));
    entity.setProduct(ProductController.productEntityFromDto(dto.getProduct()));
    entity.setScore(dto.getScore());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  public static RatingDto ratingDtoFromEntity(RatingEntity entity) {
    return new RatingDto(
        entity.getId(),
        UserController.userDtoFromEntity(entity.getUser()),
        ProductController.productDtoFromEntity(entity.getProduct()),
        entity.getScore(),
        entity.getDescription());
  }
}
