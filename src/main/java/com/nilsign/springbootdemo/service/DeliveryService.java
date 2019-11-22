package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService extends AbstractService<DeliveryEntity, Long> {
  @Autowired
  private DeliveryRepository deliveryRepository;

  @Override
  protected DeliveryRepository getRepository() {
    return deliveryRepository;
  }
}
