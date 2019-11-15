package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
