package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class OrderRepositoryExtendedImpl implements OrderRepositoryExtended {

  @Autowired
  private EntityManager entityManager;

  // Springs JpaRepository supports a large amount of keywords to help you to build your queries ;)
  // Replace by JpaRepository method -> findByProduct_Id(Long productId)
  @Override
  public Set<OrderEntity> findByProductId(@NotNull @Positive Long productId) {
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

  // Springs JpaRepository supports a large amount of keywords to help you to build your queries ;)
  // Replace by JpaRepository method -> findByIdIn(Set<Long> ids)
  @Override
  public Set<OrderEntity> byMultipleIds(@NotNull @NotEmpty Set<Long> orderIds) {
    return new HashSet<>(entityManager
        .createQuery(
            "FROM OrderEntity o WHERE o.id IN (:ids)",
            OrderEntity.class)
        .setParameter("ids", orderIds)
        .getResultList());
  }
}
