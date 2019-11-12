package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.repository.CustomerRepository;
import com.nilsign.springbootdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public Optional<ProductEntity> save(ProductEntity product) {
    return Optional.of(productRepository.save(product));
  }

  public List<ProductEntity> findAll() {
    return productRepository.findAll();
  }

  public Optional<ProductEntity> findById(Long id) {
    return productRepository.findById(id);
  }

  public void deleteById(long id) {
    productRepository.deleteById(id);
  }
}
