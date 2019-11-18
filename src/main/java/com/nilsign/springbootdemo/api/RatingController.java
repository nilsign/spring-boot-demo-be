package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.RatingDto;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController  extends AbstractController<RatingDto, RatingEntity, Long> {
  @Autowired
  private RatingService ratingService;

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
    entity.setScore(dto.getScore());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  public static RatingDto ratingDtoFromEntity(RatingEntity entity) {
    return new RatingDto(
        entity.getId(),
        UserController.userDtoFromEntity(entity.getUser()),
        entity.getScore(),
        entity.getDescription());
  }
}
