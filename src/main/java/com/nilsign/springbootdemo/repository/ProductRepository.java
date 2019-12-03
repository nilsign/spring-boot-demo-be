package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
    extends ProductRepositoryExtended, JpaRepository<ProductEntity, Long> {
}
