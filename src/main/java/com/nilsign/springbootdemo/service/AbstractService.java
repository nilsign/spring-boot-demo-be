package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.AbstractEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class AbstractService<T1 extends AbstractEntity, T2> {
  abstract protected JpaRepository<T1, T2> getRepository();

  public Optional<T1> save(T1 entity) {
    return Optional.of(getRepository().save(entity));
  }

  public EntityArrayList<T1> findAll() {
    EntityArrayList<T1> entities = new EntityArrayList<>();
    getRepository().findAll().forEach(entity -> entities.add(entity));
    return entities;
  }

  public Optional<T1> findById(T2 id) {
    return getRepository().findById(id);
  }

  public void deleteById(T2 id) {
    getRepository().deleteById(id);
  }
}
