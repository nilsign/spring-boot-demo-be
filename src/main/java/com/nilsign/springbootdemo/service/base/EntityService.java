package com.nilsign.springbootdemo.service.base;

import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Service
public abstract class EntityService<T1 extends SequencedEntity, T2> {

  protected abstract JpaRepository<T1, T2> getRepository();

  public List<T1> findAll() {
    return getRepository().findAll();
  }

  public Optional<T1> findById(@NotNull @Positive T2 id) {
    return getRepository().findById(id);
  }

  public Optional<T1> save(@NotNull T1 entity) {
    return Optional.of(getRepository().save(entity));
  }

  public void deleteById(@NotNull @Positive T2 id) {
    getRepository().deleteById(id);
  }
}
