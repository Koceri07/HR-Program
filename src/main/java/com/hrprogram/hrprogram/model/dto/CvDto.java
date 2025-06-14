package com.hrprogram.hrprogram.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvDto {
    private Long id;
    private String cvName;
    private String contactInfo;
    private String education;
    private String predictedScore;
    private String filePath;
    private boolean isActive;
    private LocalDateTime createTime;
}
