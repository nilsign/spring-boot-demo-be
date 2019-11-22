package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.UserDto;
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
import java.util.ArrayList;
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

  public static UserEntity fromDto(UserDto dto) {
    UserEntity entity = new UserEntity();
    entity.setId(dto.getId());
    entity.setRoles(dto.getRoles()
        .stream()
        .map(RoleEntity::fromDto)
        .collect(Collectors.toList()));
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    return entity;
  }

  public void addRole(RoleEntity role) {
    if (roles == null) {
      roles = new ArrayList<>();
    }
    roles.add(role);
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
