package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.service.CustomerService;
import com.nilsign.springbootdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
  
  @Autowired
  private ProductService productService;

  @GetMapping
  public List<ProductEntity> findAll() {
    return productService.findAll();
  }

  @GetMapping(path = "{id}")
  public Optional<ProductEntity> getById(
      @NotNull @PathVariable long id) {
    return productService.findById(id);
  }

  @PostMapping
  public Optional<ProductEntity> insert(
      @NotNull @Valid @RequestBody ProductEntity customer) {
    return productService.save(customer);
  }

  @PutMapping
  public Optional<ProductEntity> update(
      @NotNull @Valid @RequestBody ProductEntity customer) {
    return productService.save(customer);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(
      @NotNull @PathVariable("id") long id) {
    productService.deleteById(id);
  }
}
