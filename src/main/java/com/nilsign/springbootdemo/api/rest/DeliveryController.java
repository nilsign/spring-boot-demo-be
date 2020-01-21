package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.delivery.dto.DeliveryDto;
import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import com.nilsign.springbootdemo.domain.delivery.service.DeliveryDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController extends Controller<DeliveryDto, DeliveryEntity, Long> {

  @Autowired
  private DeliveryDtoService deliveryDtoService;

  @Override
  protected DeliveryDtoService getDtoService() {
    return deliveryDtoService;
  }
}
