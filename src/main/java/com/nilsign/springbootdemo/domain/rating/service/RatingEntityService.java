package com.nilsign.springbootdemo.domain.rating.service;

import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.rating.entity.RatingEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.rating.repository.RatingRepository;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Service
public class RatingEntityService extends EntityService<RatingEntity, Long> {

  @Autowired
  private RatingRepository ratingRepository;

  @Override
  protected RatingRepository getRepository() {
    return ratingRepository;
  }

  public List<RatingEntity> findByProductId(@NotNull @Positive Long id) {
    return ratingRepository.findByProductId(id);
  }

  public Optional<RatingEntity> findByProductAndUser(
      @NotNull ProductEntity productEntity,
      @NotNull UserEntity userEntity) {
    return ratingRepository.findByProductAndUser(productEntity, userEntity);
  }
}
