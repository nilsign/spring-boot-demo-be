package com.nilsign.springbootdemo.service.base;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class DtoService<T1 extends Dto, T2 extends SequencedEntity, T3> {

  protected abstract EntityService<T2, T3> getEntityService();

  protected abstract T2 toEntity(T1 dto);

  protected abstract T1 toDto(T2 entity);

  public List<T1> findAll() {
    return getEntityService().findAll()
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  public Optional<T1> findById(T3 id) {
    return Optional.ofNullable(toDto(
        getEntityService().findById(id)
            .get()));
  }

  public Optional<T1> save(T1 dto) {
    return Optional.of(toDto(
        getEntityService().save(toEntity(dto))
            .get()));
  }

  public void deleteById(T3 id) {
    getEntityService().deleteById(id);
  }
}
