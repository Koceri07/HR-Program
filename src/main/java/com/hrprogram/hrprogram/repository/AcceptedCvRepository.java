package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.AcceptedCvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcceptedCvRepository extends JpaRepository<AcceptedCvEntity, Long> {
    List<AcceptedCvEntity> findAll();
}
