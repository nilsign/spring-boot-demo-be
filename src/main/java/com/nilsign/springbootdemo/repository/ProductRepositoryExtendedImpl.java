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

  // Springs JpaRepository supports a large amount of keywords to help you to build your queries ;)
  // Replace by JpaRepository method -> findByOrder_Id(Long orderId)
  @Override
  public Set<ProductEntity> findByOrderId(@NotNull @Positive Long orderId) {
    return new HashSet<>(entityManager
        .createQuery(
            "FROM ProductEntity p JOIN p.orders o WHERE o.id = :orderId",
            ProductEntity.class)
        .setParameter("orderId", orderId)
        .getResultList());
  }

  // Springs JpaRepository supports a large amount of keywords to help you to build your queries ;)
  // Replace by JpaRepository method -> findByIdIn(Set<Long> ids)
  @Override
  public Set<ProductEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> productIds) {
    // Why are you using a HashSet here? This looks not very performant. Check here and in the other repositories as well.
    return new HashSet<>(entityManager
        .createQuery(
            "FROM ProductEntity p WHERE p.id IN (:ids)",
            ProductEntity.class)
        .setParameter("ids", productIds)
        .getResultList());
  }
}
