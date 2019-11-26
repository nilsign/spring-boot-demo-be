package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_user")
public class UserEntity extends SequencedEntity {
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  // Uni-directional many-to-many relation.
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
    return UserDto.builder()
        .id(super.getId())
        .roles(toDtoArrayList(roles))
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .build();
  }
}
