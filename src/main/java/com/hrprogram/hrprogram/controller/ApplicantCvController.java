package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.request.ApplicantCvRequest;
import com.hrprogram.hrprogram.service.ApplicantCvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/applicant-cvs")
@RequiredArgsConstructor
public class ApplicantCvController {
    private final ApplicantCvService applicantCvService;

    @PostMapping
    public void save(@RequestBody ApplicantCvRequest applicantCvRequest){
        applicantCvService.saveCvFile(applicantCvRequest);
    }
}
