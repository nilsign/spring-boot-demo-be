package com.nilsign.springbootdemo.entity.helper;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class EntityArrayList<T1 extends SequencedEntity> extends ArrayList<T1> {

  public <T2 extends Dto> DtoArrayList<T2> toDtos() {
    DtoArrayList<T2> dtos = new DtoArrayList();
    this.forEach(entity -> dtos.add(entity.toDto()));
    return dtos;
  }

  public static String toString(List<? extends SequencedEntity> list) {
    StringJoiner stringJoiner = new StringJoiner(
        ", ", EntityArrayList.class.getSimpleName() + "[", "]");
    list.forEach(entity -> stringJoiner.add(String.valueOf(entity.getId())));
    return stringJoiner.toString();
  }
}
