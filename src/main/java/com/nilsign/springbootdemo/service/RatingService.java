package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends AbstractService<RatingEntity, Long> {
  @Autowired
  private RatingRepository ratingRepository;

  @Override
  protected RatingRepository getRepository() {
    return ratingRepository;
  }
}
