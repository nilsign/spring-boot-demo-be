package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.StringJoiner;

public class RatingEntity extends AbstractEntity {
  @Getter @Setter
  @ManyToOne(
    fetch = FetchType.LAZY,
    cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Getter @Setter
  private float score;

  @Setter @Getter
  private String description;

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + RatingEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id=" + super.getId())
        .add("\n\t" + "user=" + user)
        .add("\n\t" + "score=" + score)
        .add("\n\t" + "description='" + description + "'")
        .toString();
  }
}
