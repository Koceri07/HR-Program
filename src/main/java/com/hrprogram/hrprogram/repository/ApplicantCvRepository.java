package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.ApplicantCvEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantCvRepository extends CrudRepository<ApplicantCvEntity,Long> {
    List<ApplicantCvEntity> findAll();

    boolean existsByFileHash(String fileHash);
}
