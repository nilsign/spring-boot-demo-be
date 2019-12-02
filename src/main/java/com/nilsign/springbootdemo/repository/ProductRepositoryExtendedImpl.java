package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ProductRepositoryExtendedImpl implements ProductRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Set<ProductEntity> findByOrderId(Long orderId) {
    // TODO(nilsHeumer): Test, check whether results are as expected.
    return new HashSet<>(entityManager
        .createQuery(
            "SELECT DISTINCT p FROM ProductEntity p"
                + " JOIN p.orders o WHERE o.id = :orderId",
            ProductEntity.class)
        .setParameter(":orderId", orderId)
        .getResultList());
  }

  public Set<ProductEntity> byMultipleIds(Set<Long> productIds) {
    return new HashSet<>(entityManager
        .createQuery(
            "SELECT p FROM ProductEntity p WHERE p.id IN (:ids)",
            ProductEntity.class)
        .setParameter(":ids", productIds)
        .getResultList());
  }
}
