package com.nilsign.springbootdemo.entity.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class SequencedEntity extends Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
