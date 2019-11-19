package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService<ProductEntity, Long> {
  private ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  protected ProductRepository getRepository() {
    return productRepository;
  }
}
