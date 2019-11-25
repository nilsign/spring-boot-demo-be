package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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

  // Uni-directional many-to-many relation.
  @Getter @Setter
  @ManyToMany(
      fetch = FetchType.EAGER,

  cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH})
  @JoinTable(
      name = "tbl_user_tbl_role",
      joinColumns = @JoinColumn(
          name = "user_id",
          referencedColumnName = "id",
          nullable = false),
      inverseJoinColumns = @JoinColumn(
          name = "role_id",
          referencedColumnName = "id",
          nullable = false))
  private List<RoleEntity> roles;

  // Bi-directional one-to-one relation.
  @Getter @Setter
  @OneToOne(
      mappedBy = "user",
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH})
  private CustomerEntity customer;

  public void addRole(RoleEntity role) {
    if (roles == null) {
      roles = new EntityArrayList();
    }
    roles.add(role);
  }

  @Override
  public UserDto toDto() {
    return new UserDto(super.getId(), toDtos(roles), firstName, lastName, email);
  }

  public String toString() {
    String appendedRoleNames = roles
        .stream()
        .map(Object::toString)
        .collect(Collectors.joining(","));
    return new StringJoiner(", ", "\n" + UserEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id='" + super.getId() + "'")
        .add("\n\t" + "roles='" + appendedRoleNames)
        .add("\n\t" + "firstName='" + firstName + "'")
        .add("\n\t" + "lastName='" + lastName + "'")
        .add("\n\t" + "email='" + email + "'")
        .add("\n\t" + "customer='" + customer + "'")
        .toString();
  }
}
