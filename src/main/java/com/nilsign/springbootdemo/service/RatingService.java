package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends AbstractService<RatingEntity, Long> {
  private RatingRepository ratingRepository;

  public RatingService(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  @Override
  protected RatingRepository getRepository() {
    return ratingRepository;
  }
}
