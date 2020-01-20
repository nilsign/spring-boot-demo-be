package com.nilsign.springbootdemo.domain.order.repository;

import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
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
public class OrderRepositoryExtendedImpl implements OrderRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  // This implementation is for demo purposes, as Spring Boot supports this query right out of the
  // box. The corresponding JpaRepository method is findByProduct_Id(Long productId).
  @Override
  public List<OrderEntity> findByProductId(@NotNull @Positive Long productId) {
    List<Object[]> resultList = entityManager
        .createQuery(
            "SELECT DISTINCT o FROM OrderEntity o JOIN o.products p WHERE p.id = :productId")
        .setParameter("productId", productId)
        .getResultList();
    List<OrderEntity> orderEntities = new ArrayList<>();
    resultList.forEach((Object[] result) -> {
      if (result.length > 0) {
        orderEntities.add((OrderEntity) result[0]);
      }
    });
    return orderEntities;
  }

  // This implementation is for demo purposes, as Spring Boot supports this query right out of the
  // box. The corresponding JpaRepository method is findByIdIn(Set<Long> orderIds).
  @Override
  public List<OrderEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> orderIds) {
    return new ArrayList<>(entityManager
        .createQuery(
            "SELECT o FROM OrderEntity o WHERE o.id IN (:ids)",
            OrderEntity.class)
        .setParameter("ids", orderIds)
        .getResultList());
  }
}
