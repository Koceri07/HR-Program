package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.AcceptMailtemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcceptMailtemplateRepository extends JpaRepository<AcceptMailtemplateEntity, Long> {
    List<AcceptMailtemplateEntity> findAll();

    AcceptMailtemplateEntity getAcceptMailtemplateEntityByHrId(Long hrId);
}
