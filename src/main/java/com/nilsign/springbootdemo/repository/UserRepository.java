package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserRepositoryExtended, JpaRepository<UserEntity, Long> {
}
