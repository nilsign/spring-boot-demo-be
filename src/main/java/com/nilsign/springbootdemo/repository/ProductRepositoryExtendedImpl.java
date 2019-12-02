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

  public Set<ProductEntity> byMultipleIds(Set<Long> productIds) {
    return new HashSet<ProductEntity>(entityManager
        .createQuery(
            "select p from ProductEntity p where p.id in (:ids)",
            ProductEntity.class)
        .setParameter(":ids", productIds)
        .getResultList());
  }
}
