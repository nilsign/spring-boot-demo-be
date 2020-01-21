package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.product.dto.ProductDto;
import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.product.service.ProductDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController extends Controller<ProductDto, ProductEntity, Long> {

  @Autowired
  private ProductDtoService productDtoService;

  @Override
  protected ProductDtoService getDtoService() {
    return productDtoService;
  }
}
