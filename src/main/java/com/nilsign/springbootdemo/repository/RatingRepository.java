package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

  List<RatingEntity> findByProductId(Long id);
}
