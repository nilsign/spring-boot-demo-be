package com.nilsign.springbootdemo.entity.helper;

import com.nilsign.springbootdemo.dto.AbstractDto;
import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.AbstractEntity;

import java.util.ArrayList;

public class EntityArrayList<T1 extends AbstractEntity> extends ArrayList<T1> {

  public <T2 extends AbstractDto> DtoArrayList<T2> toDtos() {
    DtoArrayList<T2> dtos = new DtoArrayList();
    this.forEach(entity -> dtos.add(entity.toDto()));
    return dtos;
  }
}
