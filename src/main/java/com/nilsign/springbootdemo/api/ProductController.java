package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.ProductDto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
public class ProductController extends AbstractController<ProductDto, ProductEntity, Long> {
  @Autowired
  private ProductService productService;

  @Override
  protected ProductService getService() {
    return productService;
  }

  @Override
  protected ProductEntity entityFromDto(ProductDto dto) {
    return ProductController.productEntityFromDto(dto);
  }

  @Override
  protected ProductDto dtoFromEntity(ProductEntity entity) {
    return ProductController.productDtoFromEntity(entity);
  }

  public static ProductEntity productEntityFromDto(ProductDto dto) {
    ProductEntity entity = new ProductEntity();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setPrice(dto.getPrice());
    entity.setRatings(dto.getRatings()
        .stream()
        .map(RatingController::ratingEntityFromDto)
        .collect(Collectors.toList()));
    return entity;
  }

  public static ProductDto productDtoFromEntity(ProductEntity entity) {
    return new ProductDto(
        entity.getId(),
        entity.getName(),
        entity.getPrice(),
        entity.getRatings()
            .stream()
            .map(RatingController::ratingDtoFromEntity)
            .collect(Collectors.toList()),
        entity.getOrders()
            .stream()
            .map(OrderController::orderDtoFromEntity)
            .collect(Collectors.toList()));
  }
}
