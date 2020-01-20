package com.nilsign.springbootdemo.domain.product.service;

import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.product.repository.ProductRepository;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Service
public class ProductEntityService extends EntityService<ProductEntity, Long> {

  @Autowired
  private ProductRepository productRepository;

  @Override
  protected ProductRepository getRepository() {
    return productRepository;
  }

  public Optional<ProductEntity> findByProductNumber(@NotNull @Positive Integer productNumber) {
    return productRepository.findByProductNumber(productNumber);
  }

  public List<ProductEntity> findByOrderId(@NotNull @Positive Long orderId) {
    return productRepository.findByOrderId(orderId);
  }
}
