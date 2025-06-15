package com.hrprogram.hrprogram.repository;

import com.hrprogram.hrprogram.entity.CvEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends CrudRepository<CvEntity,Long> {
    List<CvEntity> findAll();

//    List<CvEntity> getCvEntitiesByCvStatus

    @Modifying
    @Transactional
    @Query(value = "UPDATE cv c SET c.total_predicted_score = :total_score, c.experience_predicted_score = :experince_score, c.hard_skills_predicted_score = :hard_skils_score, c.soft_skills_predicted_score = :soft_skils WHERE c.id = :id;", nativeQuery = true)
    void updateScoresById(@Param("total_score") String total_score, @Param("experince_score") String experince_score, @Param("hard_skils_score") String hardSkilsScore, @Param("soft_skils") String softSkils, @Param("id") Long id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE  cv c SET c.email = :email, c.phone = :phone WHERE id = :id", nativeQuery = true)
    void updateContactInfoById(@Param("email") String email, @Param("phone") String phone, @Param("id") Long id);

    //    CvEntity findById(Long id);

}
