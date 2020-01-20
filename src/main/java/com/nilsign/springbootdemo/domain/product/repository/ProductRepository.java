package com.nilsign.springbootdemo.domain.product.repository;

import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Repository
public interface ProductRepository
    extends ProductRepositoryExtended, JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByProductNumber(@NotNull @Positive Integer productNumber);
}
