package com.nilsign.springbootdemo.domain.customer.repository;

import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
