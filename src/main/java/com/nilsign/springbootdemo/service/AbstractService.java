package com.nilsign.springbootdemo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractService<T1, T2>  {
  abstract protected JpaRepository<T1, T2> getRepository();

  public Optional<T1> save(T1 entity) {
    return Optional.of(getRepository().save(entity));
  }

  public List<T1> findAll() {
    return getRepository().findAll();
  }

  public Optional<T1> findById(T2 id) {
    return getRepository().findById(id);
  }

  public void deleteById(T2 id) {
    getRepository().deleteById(id);
  }
}
