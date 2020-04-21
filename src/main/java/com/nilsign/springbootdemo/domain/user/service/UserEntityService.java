package com.nilsign.springbootdemo.domain.user.service;

import com.nilsign.springbootdemo.domain.EntityService;
import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.role.repository.RoleRepository;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEntityService extends EntityService<UserEntity, Long> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }

  @Override
  public Optional<UserEntity> save(@NotNull UserEntity userEntity) {
    Optional<UserEntity> userToUpdate = findByEmail(userEntity.getEmail());
    if (userToUpdate.isPresent()) {
      userToUpdate.get().setFirstName(userEntity.getFirstName());
      userToUpdate.get().setLastName(userEntity.getLastName());
      userToUpdate.get().setRoles(userEntity.getRoles());
      // Updates user and role mapping.
      return super.save(userToUpdate.get());
    }
    // Create and save a new entity.
    Set<RoleEntity> roleEntities = new HashSet<>();
    userEntity.getRoles().forEach(detachedRoleEntity -> roleRepository
        .findByRoleType(detachedRoleEntity.getRoleType())
        .ifPresent(roleEntities::add));
    userEntity.setRoles(roleEntities);
    // Creates a new user and role mapping.
    return super.save(userEntity);
  }

  public Optional<UserEntity> findByEmail(@NotNull @NotBlank @Email String email) {
    return getRepository().findByEmail(email);
  }

}
