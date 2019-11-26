package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.AbstractDto;
import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

// TODO(nilsheumer): Convert this class to an interface. Create an abstract GloballySequencedEntity
// class, and place it in between the concrete entity class and this interface/class. Do this
// everywhere int the codebase.
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public abstract <T extends AbstractDto> T toDto();

  protected <T extends AbstractDto> DtoArrayList<T> toDtoArrayList(
      List<? extends AbstractEntity> entities) {
    DtoArrayList<T> dtos = new DtoArrayList<>();
    entities.forEach(entity -> dtos.add(entity.toDto()));
    return dtos;
  }
}
