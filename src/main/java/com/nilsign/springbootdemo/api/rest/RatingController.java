package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.base.Controller;
import com.nilsign.springbootdemo.dto.RatingDto;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.RatingDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController extends Controller<RatingDto, RatingEntity, Long> {

  @Autowired
  private RatingDtoService ratingDtoService;

  @Override
  protected RatingDtoService getDtoService() {
    return ratingDtoService;
  }
}
