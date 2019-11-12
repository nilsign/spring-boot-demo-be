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

@RestController
public abstract class AbstractController<T1, T2> {
  protected abstract AbstractService<T1, T2> getService();

  @GetMapping
  public List<T1> findAll() {
    return getService().findAll();
  }

  @GetMapping(path = "{id}")
  public Optional<T1> getById(
      @NotNull @PathVariable T2 id) {
    return getService().findById(id);
  }

  @PostMapping
  public Optional<T1> insert(
      @NotNull @Valid @RequestBody T1 entity) {
    return getService().save(entity);
  }

  @PutMapping
  public Optional<T1> update(
      @NotNull @Valid @RequestBody T1 entity) {
    return getService().save(entity);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(
      @NotNull @PathVariable("id") T2 id) {
    getService().deleteById(id);
  }
}
