package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ProductRepositoryExtendedImpl implements ProductRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  // This implementation is for demo purposes, as Spring Boot supports this query right out of the
  // box. The corresponding JpaRepository method is findByOrder_Id(Long orderId).
  @Override
  public List<ProductEntity> findByOrderId(@NotNull @Positive Long orderId) {
    return new ArrayList<>(entityManager
        .createQuery(
            "SELECT DISTINCT p FROM ProductEntity p JOIN p.orders o WHERE o.id = :orderId",
            ProductEntity.class)
        .setParameter("orderId", orderId)
        .getResultList());
  }

  // This implementation is for demo purposes, as Spring Boot supports this query right out of the
  // box. The corresponding JpaRepository method is findByIdIn(Set<Long> productIds).
  @Override
  public List<ProductEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> productIds) {
    return new ArrayList<>(entityManager
        .createQuery(
            "SELECT p FROM ProductEntity p WHERE p.id IN (:ids)",
            ProductEntity.class)
        .setParameter("ids", productIds)
        .getResultList());
  }
}
