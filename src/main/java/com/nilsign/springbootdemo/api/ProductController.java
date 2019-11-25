package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController extends AbstractController<ProductDto, ProductEntity, Long> {
  @Autowired
  private ProductService productService;

  @Override
  protected ProductService getService() {
    return productService;
  }
}
