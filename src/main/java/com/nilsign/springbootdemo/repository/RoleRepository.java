package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends RoleRepositoryExtended, JpaRepository<RoleEntity, Long> {
}
