package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Repository
public class OrderRepositoryExtendedImpl implements OrderRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Set<OrderEntity> findByProductId(Long productId) {
    // TODO(nilsHeumer): Test, check whether results are as expected.
    return new HashSet<>(entityManager
        .createQuery(
            "SELECT DISTINCT o FROM OrderEntity o"
            + " JOIN o.products p WHERE p.id = :productId",
            OrderEntity.class)
        .setParameter(":productId", productId)
        .getResultList());
  }

  @Override
  public Set<OrderEntity> byMultipleIds(Set<Long> orderIds) {
    return new HashSet<>(entityManager
        .createQuery(
            "SELECT o FROM OrderEntity o WHERE o.id IN (:ids)",
            OrderEntity.class)
        .setParameter(":ids", orderIds)
        .getResultList());
  }
}
