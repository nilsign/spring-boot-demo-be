package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.repository.RatingRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingEntityService extends EntityService<RatingEntity, Long> {

  @Autowired
  private RatingRepository ratingRepository;

  @Override
  protected RatingRepository getRepository() {
    return ratingRepository;
  }

  public List<RatingEntity> findByProductId(Long id) {
    return ratingRepository.findByProductId(id);
  }
}
