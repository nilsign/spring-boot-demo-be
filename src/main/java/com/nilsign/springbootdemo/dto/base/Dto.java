package com.nilsign.springbootdemo.dto.base;

import com.nilsign.springbootdemo.entity.base.SequencedEntity;

public interface Dto {
  <T extends SequencedEntity> T toEntity();

  String toString();
}
