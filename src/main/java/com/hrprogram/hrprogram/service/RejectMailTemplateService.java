package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.entity.MailEntity;
import com.hrprogram.hrprogram.entity.RejectMailTemplateEntity;
import com.hrprogram.hrprogram.exception.NotFoundException;
import com.hrprogram.hrprogram.mapper.RejectedMailMapper;
import com.hrprogram.hrprogram.mapper.UserMapper;
import com.hrprogram.hrprogram.model.request.MailRequest;
import com.hrprogram.hrprogram.model.request.RejectMailTemplateRequest;
import com.hrprogram.hrprogram.repository.RejectMailTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
//@RequiredArgsConstructor
public class RejectMailTemplateService {
    private final RejectMailTemplateRepository repository;
    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final MailService mailService;
    private  CvService cvService;


    public RejectMailTemplateService(
            RejectMailTemplateRepository repository,
            JavaMailSender javaMailSender,
            UserService userService,
            MailService mailService,
            @Lazy CvService cvService // <-- DÜZGÜN LAZY BURADA
        ) {
            this.repository = repository;
            this.javaMailSender = javaMailSender;
            this.userService = userService;
            this.mailService = mailService;
            this.cvService = cvService;
        }



    public void saveRejectMailTemplate(RejectMailTemplateRequest rejectMailTemplateRequest){
        log.info("Action.saveRejectMailTemplate.start for hr id {}", rejectMailTemplateRequest.getHrId());
        RejectMailTemplateEntity rejectMailTemplate = new RejectMailTemplateEntity();
        var user = userService.getUserResponseById(rejectMailTemplateRequest.getHrId());

        rejectMailTemplate.setHrId(rejectMailTemplateRequest.getHrId());
        rejectMailTemplate.setContent(rejectMailTemplateRequest.getContent());
        rejectMailTemplate.setCreateAt(LocalDateTime.now());
        rejectMailTemplate.setMailFrom(user.getEmail());
        repository.save(rejectMailTemplate);

        log.info("Action.saveRejectMailTemplate.end for hr id {}", rejectMailTemplateRequest.getHrId());
    }

    public RejectMailTemplateRequest getRejectMailTemplateByHrId(Long hrId){
        log.info("Action.getRejectMailTemplateByHrId.start for hr id {}", hrId);

        var rejectMailTemplate = repository.getRejectMailTemplateEntityByHrId(hrId);

        if (rejectMailTemplate == null) {
            log.error("RejectMailTemplate not found for hrId {}", hrId);
            throw new NotFoundException("RejectMailTemplate not found");
        }

        var rejectMailTemplateRequest = RejectedMailMapper.INSTANCE.entityToRequest(rejectMailTemplate);
        log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
        return rejectMailTemplateRequest;
    }



    public void sendRejectMail(Long hrId, Long cvId){
        log.info("Action.sendRejectMail.start");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        RejectMailTemplateRequest rejectMailTemplateRequest = getRejectMailTemplateByHrId(hrId);

        var cvResponse = cvService.getCvResponseById(cvId);
        var hrResponse = userService.getUserResponseById(hrId);
        String content = rejectMailTemplateRequest.getContent();

        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(hrResponse.getEmail());
        simpleMailMessage.setTo(cvResponse.getEmail());

        javaMailSender.send(simpleMailMessage);
        log.info("Action.sendRejectMail.end");
    }


}
