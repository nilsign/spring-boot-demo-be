package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class OrderRepositoryExtendedImpl implements OrderRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Set<OrderEntity> findByProductId(Long productId) {
    // TODO(nilsHeumer): Test, check whether results are as expected, when there order products!
    if (productId == null) {
      return new HashSet<>();
    }
    List<Object[]> resultList = entityManager
        .createQuery(
            "FROM OrderEntity o JOIN o.products p WHERE p.id = :productId")
        .setParameter("productId", productId)
        .getResultList();
    Set<OrderEntity> orderEntities = new HashSet<>();
    resultList.forEach((Object[] result) -> {
      if (result.length > 0) {
        orderEntities.add((OrderEntity) result[0]);
      }
    });
    return orderEntities;
  }

  @Override
  public Set<OrderEntity> byMultipleIds(Set<Long> orderIds) {
    return new HashSet<>(entityManager
        .createQuery(
            "FROM OrderEntity o WHERE o.id IN (:ids)",
            OrderEntity.class)
        .setParameter("ids", orderIds)
        .getResultList());
  }
}
