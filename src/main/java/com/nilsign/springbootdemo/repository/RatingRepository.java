package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.entity.RatingEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

  List<RatingEntity> findByProductId(
      @NotNull @Positive Long id);

  Optional<RatingEntity> findByProductAndUser(
      @NotNull ProductEntity productEntity,
      @NotNull UserEntity userEntity);
}
