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



    public void saveRejectMailTemplate(Long hrId, String content){
        log.info("Action.saveRejectMailTemplate.start for hr id {}", hrId);
        RejectMailTemplateEntity rejectMailTemplate = new RejectMailTemplateEntity();
        var user = userService.getUserResponseById(hrId);

        MailEntity mailEntity = new MailEntity();
//        mailEntity.setMailTo(List.of(cvRequest.getEmail()));
        mailEntity.setMailFrom(user.getEmail());
        mailEntity.setText(content);

        rejectMailTemplate.setHrId(hrId);
        rejectMailTemplate.setContent(content);
        rejectMailTemplate.setCreateAt(LocalDateTime.now());
        rejectMailTemplate.setMailEntity(mailEntity);
        repository.save(rejectMailTemplate);

        log.info("Action.saveRejectMailTemplate.end for hr id {}", hrId);
    }

    public RejectMailTemplateRequest getRejectMailTemplateByHrId(Long hrId){
        log.info("Action.getRejectMailTemplateByHrId.start for hr id {}", hrId);

        var rejectMailTemplate = repository.getRejectMailTemplateEntityByHrId(hrId);

        if (rejectMailTemplate == null) {
            log.error("RejectMailTemplate not found for hrId {}", hrId);
            throw new NotFoundException("RejectMailTemplate not found");
        }

        // Bu yoxlamanı sil — əgər çox istəsən, warning log at, amma null dönmə
        // if (rejectMailTemplate.getContent().isEmpty()) { ... }

        var rejectMailTemplateRequest = RejectedMailMapper.INSTANCE.entityToRequest(rejectMailTemplate);
        log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
        return rejectMailTemplateRequest;
    }


//    public void sendRejectMail(Long hrId, Long cvId){
//        log.info("Action.sendRejectMail.start");
//        var mailTemplate = getRejectMailTemplateByHrId(hrId);
//        var cvResponse = cvService.getCvResponseById(cvId);
//        mailTemplate.setEmails(List.of(cvResponse.getEmail()));
////        var mailRequest = mailTemplate.getMailRequest();
//        var mailRequest = new MailRequest();
//        var userResponse = userService.getUserResponseById(hrId);
//        var userRequest = UserMapper.INSTANCE.responseToRequest(userResponse);
//        mailRequest.setMailFrom(userResponse.getEmail());
//        mailRequest.setMailTo(List.of(cvResponse.getEmail()));
//        mailService.sendMail(mailRequest, userRequest);
//        log.info("Action.sendRejectMail.end");
//    }


    public void sendRejectMail(Long hrId, Long cvId){
        log.info("Action.sendRejectMail.start");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        var cvResponse = cvService.getCvResponseById(cvId);
        var hrResponse = userService.getUserResponseById(hrId);


    }




//    public void sendRejectMail(Long hrId, Long cvId) {
//        log.info("Action.sendRejectMail.start");
//
//        RejectMailTemplateEntity mailTemplate = getRejectMailTemplateByHrId(hrId);
//        CvResponse cvResponse = cvService.getCvResponseById(cvId);
//
//        if (mailTemplate == null) {
//            throw new IllegalStateException("Mail template not found for HR ID: " + hrId);
//        }
//
//        if (cvResponse == null || cvResponse.getEmail() == null) {
//            throw new IllegalArgumentException("CV response or email is null for CV ID: " + cvId);
//        }
//
//        // Email siyahısını set edirik (list şəklində)
//        mailTemplate.setEmails(List.of(cvResponse.getEmail()));
//
//        MailRequest mailRequest = mailTemplate.getMailRequest();
//        if (mailRequest == null) {
//            throw new IllegalStateException("Mail request is null in template for HR ID: " + hrId);
//        }
//
//        UserResponse userResponse = userService.getUserResponseById(hrId);
//        if (userResponse == null) {
//            throw new IllegalStateException("User not found for HR ID: " + hrId);
//        }
//
//        UserRequest userRequest = UserMapper.INSTANCE.responseToRequest(userResponse);
//        mailService.sendMail(mailRequest, userRequest);
//
//        log.info("Action.sendRejectMail.end");
//    }


}
