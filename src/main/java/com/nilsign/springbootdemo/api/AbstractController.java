package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
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
import java.util.Optional;

@RestController
public abstract class AbstractController<T1 extends Dto, T2 extends SequencedEntity, T3> {
  protected abstract AbstractService<T2, T3> getService();

  @GetMapping
  public DtoArrayList<T1> findAll() {
    return getService().findAll().toDtos();
  }

  @GetMapping(path = "{id}")
  public Optional<T1> getById(
      @NotNull @PathVariable T3 id) {
    Optional<T2> result = getService().findById(id);
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(result.get().toDto());
  }

  @PostMapping
  public Optional<T1> insert(
      @NotNull @Valid @RequestBody T1 dto) {
    Optional<T2> result = getService().save(dto.toEntity());
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(result.get().toDto());
  }

  @PutMapping
  public Optional<T1> update(
      @NotNull @Valid @RequestBody T1 dto) {
    Optional<T2> result = getService().save(dto.toEntity());
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(result.get().toDto());
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(
      @NotNull @PathVariable("id") T3 id) {
    getService().deleteById(id);
  }
}
