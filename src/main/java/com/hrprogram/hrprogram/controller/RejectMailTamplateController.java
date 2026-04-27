package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.model.request.RejectMailTemplateRequest;
import com.hrprogram.hrprogram.response.CvResponse;
import com.hrprogram.hrprogram.service.RejectMailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reject-mail")
@RequiredArgsConstructor
public class RejectMailTamplateController {

    private final RejectMailTemplateService rejectMailTemplateService;

    @PostMapping
    public void save(@RequestBody RejectMailTemplateRequest rejectMailTemplateRequest){
        rejectMailTemplateService.saveRejectMailTemplate(rejectMailTemplateRequest);
    }

    @GetMapping("/{hrId}")
    public RejectMailTemplateRequest getByHrId(@PathVariable Long hrId){
        return rejectMailTemplateService.getRejectMailTemplateByHrId(hrId);
    }

}
