package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
