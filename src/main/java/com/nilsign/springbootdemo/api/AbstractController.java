package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.service.AbstractService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public abstract class AbstractController<T1, T2, T3> {
  protected abstract AbstractService<T2, T3> getService();

  protected abstract T2 entityFromDto(T1 dto);
  protected abstract T1 dtoFromEntity(T2 entity);

  protected List<T1> dtosFromEntities(List<T2> entities) {
    return entities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
  }

  @GetMapping
  public List<T1> findAll() {
    return dtosFromEntities(getService().findAll());
  }

  @GetMapping(path = "{id}")
  public Optional<T1> getById(
      @NotNull @PathVariable T3 id) {
    Optional<T2> result = getService().findById(id);
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(dtoFromEntity(result.get()));
  }

  @PostMapping
  public Optional<T1> insert(
      @NotNull @Valid @RequestBody T1 dto) {
    Optional<T2> result = getService().save(entityFromDto(dto));
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(dtoFromEntity(result.get()));
  }

  @PutMapping
  public Optional<T1> update(
      @NotNull @Valid @RequestBody T1 dto) {
    Optional<T2> result = getService().save(entityFromDto(dto));
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(dtoFromEntity(result.get()));
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(
      @NotNull @PathVariable("id") T3 id) {
    getService().deleteById(id);
  }
}
