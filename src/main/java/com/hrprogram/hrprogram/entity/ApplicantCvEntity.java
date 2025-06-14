package com.hrprogram.hrprogram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applicant_Cv")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantCvEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hrId;

    private String filePath;

    private String originalFileName;

    @Column(unique = true)
    private String fileHash;


    @CreationTimestamp
    private LocalDateTime createAt;

}
