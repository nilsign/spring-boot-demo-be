package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
  @Autowired
  private EntityManager entityManager;

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    try {
      return Optional.of(entityManager
          .createQuery(
              "from UserEntity user where user.email = :email",
              UserEntity.class)
          .setParameter("email", email)
          .getSingleResult());
    } catch (NoResultException e) {
      // Nothing to do here.
    }
    return Optional.empty();
  }
}
