package com.nilsign.springbootdemo.domain.address.repository;

import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
