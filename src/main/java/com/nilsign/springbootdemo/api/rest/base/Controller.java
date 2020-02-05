package com.nilsign.springbootdemo.api.rest.base;

import com.nilsign.springbootdemo.domain.Dto;
import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

// TODO(nilsheumer): Convert the @PreAuthorize restrictions to a meaningful concept. Here the
// @PreAuthorization have been only set for demo and testing purposes.
@RestController
public abstract class Controller<T1 extends Dto, T2 extends SequencedEntity, T3> {

  protected abstract DtoService<T1, T2, T3> getDtoService();

  // TODO(nilsheumer): Find a good way to distinguish role access within the different derived
  // controllers.
  @GetMapping
  // @PreAuthorize("hasRole('REALM_SUPERADMIN') OR hasRole('REALM_CLIENT_ADMIN')")
  public List<T1> findAll() {
    return getDtoService().findAll();
  }

  // @PreAuthorize("hasRole('JPA_GLOBALADMIN') OR hasRole('JPA_ADMIN')")
  @GetMapping(path = "{id}")
  public Optional<T1> findById(@NotNull @PathVariable T3 id) {
    return getDtoService().findById(id);
  }

  @PostMapping
  public Optional<T1> save(@NotNull @Valid @RequestBody T1 dto) {
    return getDtoService().save(dto);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(@NotNull @PathVariable("id") T3 id) {
    getDtoService().deleteById(id);
  }
}
