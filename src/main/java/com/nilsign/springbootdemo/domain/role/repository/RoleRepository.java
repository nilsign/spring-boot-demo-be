package com.nilsign.springbootdemo.domain.role.repository;

import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByRoleType(
      @NotNull RoleType roleType);
}
