package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.RejectMailTemplateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RejectMailTemplateRepository extends CrudRepository<RejectMailTemplateEntity,Long> {
    List<RejectMailTemplateEntity> findAll();

    List<RejectMailTemplateEntity> findAllByHrId(Long hrId);

    @Query(value = "SELECT * FROM reject_mails WHERE id = :id;", nativeQuery = true)
    RejectMailTemplateEntity getRejectMailTemplateEntityByHrId(@Param("id") Long hrId);
}
