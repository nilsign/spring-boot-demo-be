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

  // TODO(nilsheumer): User string instead of number for string interpolation.
  // TODO(nilsheumer): Try to get rid of the name rol. Check whether role is a keyword.
  @Override
  public Optional<RoleEntity> findByRoleType(RoleType roleType) {
    try {
      return Optional.of(entityManager
          .createQuery(
              "from RoleEntity rol where rol.roleType = ?1",
              RoleEntity.class)
          .setParameter(1, roleType)
          .getSingleResult());
    } catch (NoResultException e) {
      // Nothing to do here.
    }
    return Optional.empty();
  }
}
