package com.nilsign.springbootdemo.api.base;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public abstract class Controller<T1 extends Dto, T2 extends SequencedEntity, T3> {

  protected abstract DtoService<T1, T2, T3> getDtoService();

  // TODO(nilsheumer): Find a good way to distinguish role access within the different derived
  // controllers. A custom annotation might be a good solution for this.
  @GetMapping
  @RolesAllowed({"GLOBALADMIN"})
  public List<T1> findAll() {
    return getDtoService().findAll();
  }

  @RolesAllowed({"BUYER"})
  @GetMapping(path = "{id}")
  public Optional<T1> findById(@NotNull @PathVariable T3 id) {
    return getDtoService().findById(id);
  }

  @PostMapping
  public Optional<T1> insert(@NotNull @Valid @RequestBody T1 dto) {
    return getDtoService().save(dto);
  }

  @PutMapping
  public Optional<T1> update(@NotNull @Valid @RequestBody T1 dto) {
    return getDtoService().save(dto);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(@NotNull @PathVariable("id") T3 id) {
    getDtoService().deleteById(id);
  }
}
