package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService<ProductEntity, Long> {
  @Autowired
  private ProductRepository productRepository;

  @Override
  protected ProductRepository getService() {
    return productRepository;
  }
}
