package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.CvEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends CrudRepository<CvEntity,Long> {
    List<CvEntity> findAll();

//    CvEntity findById(Long id);

}
