package com.nilsign.springbootdemo.dto.helper;

import com.nilsign.springbootdemo.dto.AbstractDto;
import com.nilsign.springbootdemo.entity.AbstractEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;

import java.util.ArrayList;

public class DtoArrayList<T1 extends AbstractDto> extends ArrayList<T1> {

  public <T2 extends AbstractEntity> EntityArrayList<T2> toEntities() {
    EntityArrayList<T2> entities = new EntityArrayList<>();
    this.forEach(dto -> entities.add(dto.toEntity()));
    return entities;
  }
}