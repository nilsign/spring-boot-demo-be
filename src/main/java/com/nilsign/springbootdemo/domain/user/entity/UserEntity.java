package com.nilsign.springbootdemo.domain.user.entity;

import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true, exclude = "customer")
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
  private Set<RoleEntity> roles;

  // Bi-directional one-to-one relation.
  @ToString.Exclude
  @OneToOne(
      mappedBy = "user",
      fetch = FetchType.LAZY,
      cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
  private CustomerEntity customer;

  public static UserEntity create(
      @NotNull UserDto userDto,
      CustomerEntity customerEntity) {
    return UserEntity.builder()
        .id(userDto.getId())
        .email(userDto.getEmail())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .roles(userDto.getRoles()
            .stream()
            .map(RoleEntity::create)
            .collect(Collectors.toSet()))
        .customer(customerEntity)
        .build();
  }
}
