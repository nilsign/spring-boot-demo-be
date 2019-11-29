package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;


@Repository
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Optional<RoleEntity> findByRoleType(RoleType roleType) {
    try {
      return Optional.of(entityManager
          .createQuery(
              "from RoleEntity role where role.roleType = :roleType",
              RoleEntity.class)
          .setParameter("roleType", roleType)
          .getSingleResult());
    } catch (NoResultException e) {
      // Nothing to do here.
    }
    return Optional.empty();
  }
}
