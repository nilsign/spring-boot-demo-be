package com.nilsign.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_user")
public class UserEntity extends AbstractEntity {
  @Getter @Setter
  @Column(name="first_name")
  private String firstName;

  @Getter @Setter
  @Column(name="last_name")
  private String lastName;

  @Getter @Setter
  @Column(name="email")
  private String email;


  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + UserEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\tid='" + super.getId() + "'")
        .add("\n\t" + "firstName='" + firstName + "'")
        .add("\n\t" + "lastName='" + lastName + "'")
        .add("\n\t" + "email='" + email + "'")
        .toString();
  }
}
