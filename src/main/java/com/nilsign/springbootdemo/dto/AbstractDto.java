package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.AbstractEntity;

public interface AbstractDto {
  <T extends AbstractEntity> T toEntity();

  String toString();
}
