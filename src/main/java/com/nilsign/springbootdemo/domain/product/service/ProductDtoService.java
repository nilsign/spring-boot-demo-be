package com.nilsign.springbootdemo.domain.product.service;

import com.nilsign.springbootdemo.domain.order.service.OrderEntityService;
import com.nilsign.springbootdemo.domain.product.dto.ProductDto;
import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.rating.entity.RatingEntity;
import com.nilsign.springbootdemo.domain.rating.service.RatingEntityService;
import com.nilsign.springbootdemo.domain.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

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
  protected ProductEntity toEntity(@NotNull ProductDto productDto) {
    List<RatingEntity> ratings = ratingEntityService.findByProductId(productDto.getId());
    List<OrderEntity> orders = orderEntityService.findByProductId(productDto.getId());
    return ProductEntity.create(productDto, ratings, orders);
  }

  @Override
  protected ProductDto toDto(@NotNull ProductEntity productEntity) {
    return ProductDto.create(productEntity);
  }
}
