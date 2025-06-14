package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.service.RejectMailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RejectMailTamplateController {

    private final RejectMailTemplateService rejectMailTemplateService;

    @PostMapping
    public void save(Long hrId, String content){
        rejectMailTemplateService.saveRejectMailTemplate(hrId,content);
    }

    @GetMapping
    public String getByHrId(Long hrId){
        return rejectMailTemplateService.getRejectMailTemplateByHrId(hrId);
    }

}
