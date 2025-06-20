package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.service.RejectMailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final RejectMailTemplateService rejectMailTemplateService;

    @PostMapping("/hr-id/{hrId}/cv-id/{cvId}")
    public void sendRejectMailByHrId(@PathVariable Long hrId, @PathVariable Long cvId){
        rejectMailTemplateService.sendRejectMail(hrId, cvId);
    }
}
