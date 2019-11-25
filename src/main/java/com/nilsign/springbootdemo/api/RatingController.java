package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.RatingDto;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController extends AbstractController<RatingDto, RatingEntity, Long> {
  @Autowired
  private RatingService ratingService;

  @Override
  protected RatingService getService() {
    return ratingService;
  }
}
