package com.nilsign.springbootdemo.domain.delivery.service;

import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import com.nilsign.springbootdemo.domain.delivery.repository.DeliveryRepository;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEntityService extends EntityService<DeliveryEntity, Long> {

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Override
  protected DeliveryRepository getRepository() {
    return deliveryRepository;
  }
}
