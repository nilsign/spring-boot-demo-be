package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDtoService extends DtoService<ProductDto, ProductEntity, Long> {

  @Autowired
  private ProductEntityService productEntityService;

  @Autowired
  private RatingEntityService ratingEntityService;

  @Override
  protected ProductEntityService getEntityService() {
    return productEntityService;
  }

  @Override
  protected ProductEntity toEntity(ProductDto productDto) {
    List<RatingEntity> productRatings = ratingEntityService.findByProductId(productDto.getId());
    return ProductEntity.create(productDto, productRatings);
  }

  @Override
  protected ProductDto toDto(ProductEntity productDto) {
    return ProductDto.create(productDto);
  }
}
