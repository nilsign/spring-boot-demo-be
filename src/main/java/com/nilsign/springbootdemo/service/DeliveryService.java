package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.DeliveryEntity;
import com.nilsign.springbootdemo.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService extends AbstractService<DeliveryEntity, Long> {
  private DeliveryRepository deliveryRepository;

  public DeliveryService(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  @Override
  protected DeliveryRepository getRepository() {
    return deliveryRepository;
  }
}
