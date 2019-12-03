package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.OrderEntity;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductDtoService extends DtoService<ProductDto, ProductEntity, Long> {

  @Autowired
  private ProductEntityService productEntityService;

  @Autowired
  private RatingEntityService ratingEntityService;

  @Autowired
  private OrderEntityService orderEntityService;

  @Override
  protected ProductEntityService getEntityService() {
    return productEntityService;
  }

  @Override
  protected ProductEntity toEntity(ProductDto productDto) {
    List<RatingEntity> ratings = ratingEntityService.findByProductId(productDto.getId());
    Set<OrderEntity> orders = orderEntityService.findByProductId(productDto.getId());
    return ProductEntity.create(productDto, ratings, orders);
  }

  @Override
  protected ProductDto toDto(ProductEntity productDto) {
    return ProductDto.create(productDto);
  }
}
