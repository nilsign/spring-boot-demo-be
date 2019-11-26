package com.nilsign.springbootdemo.entity.base;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@SuperBuilder
public abstract class Entity {
  @Override
  public abstract String toString();

  public abstract <T extends Dto> T toDto();

  protected <T extends Dto> DtoArrayList<T> toDtoArrayList(
      List<? extends Entity> entities) {
    DtoArrayList<T> dtos = new DtoArrayList<>();
    entities.forEach(entity -> dtos.add(entity.toDto()));
    return dtos;
  }
}
