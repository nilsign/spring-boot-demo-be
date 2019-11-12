package com.nilsign.springbootdemo.entity;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
