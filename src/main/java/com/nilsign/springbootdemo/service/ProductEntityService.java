package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.repository.ProductRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductEntityService extends EntityService<ProductEntity, Long> {

  @Autowired
  private ProductRepository productRepository;

  @Override
  protected ProductRepository getRepository() {
    return productRepository;
  }

  public Set<ProductEntity> findByOrderId(Long orderId) {
    return productRepository.findByOrderId(orderId);
  }
}
