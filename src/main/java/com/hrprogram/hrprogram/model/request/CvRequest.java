package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvRequest {
    private Long id;
    private String position;
    private String text;
    private String filePath;
    private String originalFileName;
    private List<String> keywords;
    private String email;
//    private CvDto cvDto;
}
