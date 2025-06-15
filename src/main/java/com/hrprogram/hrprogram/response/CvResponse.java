package com.hrprogram.hrprogram.response;

import com.hrprogram.hrprogram.model.enums.CvStatus;
import com.hrprogram.hrprogram.model.request.PdfRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvResponse {

    private Long id;
    private String text;
    private String email;
    private String phone;

    private String originalFileName;
    private String totalPredictedScore;
    private String experiencePredictedScore;
    private String hardSkillsPredictedScore;
    private String softSkillsPredictedScore;
    private CvStatus cvStatus;

    private boolean isActive;
    private LocalDateTime createAt;

}
