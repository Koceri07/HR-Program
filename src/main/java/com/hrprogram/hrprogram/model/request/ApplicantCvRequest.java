package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantCvRequest {

    private Long id;
    private Long hrId;
    private String filePath;
    private String originalFileName;
}
