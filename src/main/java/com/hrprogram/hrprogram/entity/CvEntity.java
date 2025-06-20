package com.hrprogram.hrprogram.entity;

import com.hrprogram.hrprogram.model.enums.CvStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CvEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;
    private String filePath;
    private String position;
    private String summary;
    private String email;
    private String phone;
    private String totalPredictedScore;
    private String experiencePredictedScore;
    private String hardSkillsPredictedScore;
    private String softSkillsPredictedScore;
//    private String languagePredictedScore;
    @Enumerated(value = EnumType.STRING)
    private CvStatus cvStatus;
    private boolean isActive;
    

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
