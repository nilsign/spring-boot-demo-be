package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ProductRepositoryExtendedImpl implements ProductRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Set<ProductEntity> findByOrderId(@NotNull @Positive Long orderId) {
    return new HashSet<>(entityManager
        .createQuery(
            "FROM ProductEntity p JOIN p.orders o WHERE o.id = :orderId",
            ProductEntity.class)
        .setParameter("orderId", orderId)
        .getResultList());
  }

  @Override
  public Set<ProductEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> productIds) {
    return new HashSet<>(entityManager
        .createQuery(
            "FROM ProductEntity p WHERE p.id IN (:ids)",
            ProductEntity.class)
        .setParameter("ids", productIds)
        .getResultList());
  }
}
