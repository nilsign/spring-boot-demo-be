package com.nilsign.springbootdemo.dto.helper;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;

import java.util.ArrayList;

public class DtoArrayList<T1 extends Dto> extends ArrayList<T1> {

  public <T2 extends SequencedEntity> EntityArrayList<T2> toEntities() {
    EntityArrayList<T2> entities = new EntityArrayList<>();
    this.forEach(dto -> entities.add(dto.toEntity()));
    return entities;
  }
}