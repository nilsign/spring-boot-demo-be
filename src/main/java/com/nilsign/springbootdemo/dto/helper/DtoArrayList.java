package com.nilsign.springbootdemo.dto.helper;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

// TODO(nilsheumer): Check, whether it is worth to have a DtoArrayList.
public class DtoArrayList<T1 extends Dto> extends ArrayList<T1> {

  public <T2 extends SequencedEntity> EntityArrayList<T2> toEntities() {
    EntityArrayList<T2> entities = new EntityArrayList<>();
    this.forEach(dto -> entities.add(dto.toEntity()));
    return entities;
  }

  // TODO(nilsheumer): Check whether this is useful.
  public static String toString(List<? extends Dto> list) {
    StringJoiner stringJoiner = new StringJoiner(
        ", ", EntityArrayList.class.getSimpleName() + "[", "]");
    list.forEach(dto -> stringJoiner.add(String.valueOf(dto.getId())));
    return stringJoiner.toString();
  }
}
